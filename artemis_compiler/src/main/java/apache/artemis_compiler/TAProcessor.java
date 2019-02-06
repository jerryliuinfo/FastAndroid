package apache.artemis_compiler;

import com.apache.artemis_annotation.TaOnClick;
import com.apache.artemis_annotation.ViewById;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;


//@AutoService(Processor.class)
//@SupportedSourceVersion(SourceVersion.RELEASE_8)//也可以采用下面的写法
//@SupportedAnnotationTypes({"TaFindId"})//此写法混淆的时候需要保留FindId类
public class TAProcessor extends AbstractProcessor {
    private Filer mFileUtils;
    private Elements mElementUtils;
    private Messager messager;
    /**
     * 一个需要生成的类的集合（key为类的全名，value为该类所有相关的需要的信息）
     */
    private Map<String, ProxyInfo> mProxyMap = new HashMap<String, ProxyInfo>();
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFileUtils = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<String>();
        annotationTypes.add(ViewById.class.getCanonicalName());
        annotationTypes.add(TaOnClick.class.getCanonicalName());
        return annotationTypes;
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        collectionInfo(roundEnvironment);
        generateClass();
        return true;
    }
    
    /**
     * 生成代理类
     */
    private void generateClass() {
        for (String key : mProxyMap.keySet()) {
            ProxyInfo proxyInfo = mProxyMap.get(key);
            JavaFileObject sourceFile = null;
            try {
                sourceFile = mFileUtils.createSourceFile(proxyInfo.getProxyClassFullName(), proxyInfo.typeElement);
                Writer writer = sourceFile.openWriter();
                writer.write(proxyInfo.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                error(proxyInfo.typeElement, "===tb===%s", e.getMessage());
            }
        }
    }
    
    /**
     * 收集所需生成类的信息
     *
     * @param roundEnvironment
     */
    private void collectionInfo(RoundEnvironment roundEnvironment) {
        //process可能会多次调用，避免生成重复的代理类
        mProxyMap.clear();
        //获得被该注解声明的类和变量
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ViewById.class);
        //收集信息
        for (Element element : elements) {
            if (element.getKind() == ElementKind.CLASS) {
                //获取注解的值
                TypeElement typeElement = (TypeElement) element;
                //类的完整路径
                String qualifiedName = typeElement.getQualifiedName().toString();
                /*类名*/
                String clsName = typeElement.getSimpleName().toString();
                /*获取包名*/
                String packageName = mElementUtils.getPackageOf(typeElement).getQualifiedName().toString();

                ViewById findId = element.getAnnotation(ViewById.class);
                if (findId != null) {
                    int value = findId.value();
                    //处理类注解
                    ProxyInfo proxyInfo = mProxyMap.get(qualifiedName);
                    if (proxyInfo == null) {
                        proxyInfo = new ProxyInfo();
                        mProxyMap.put(qualifiedName, proxyInfo);
                    }
                    
                    proxyInfo.value = value;
                    proxyInfo.typeElement = typeElement;
                    proxyInfo.packageName = packageName;
                }
            } else if (element.getKind() == ElementKind.FIELD) {
                //获取注解的值
                ViewById findId = element.getAnnotation(ViewById.class);
                if (findId != null) {
                    int value = findId.value();
                    //处理成员变量注解
                    VariableElement variableElement = (VariableElement) element;
                    //这里先要获取上层封装类型，然后强转为TypeElement
                    String qualifiedName = ((TypeElement) element.getEnclosingElement()).getQualifiedName().toString();
                    ProxyInfo proxyInfo = mProxyMap.get(qualifiedName);
                    if (proxyInfo == null) {
                        proxyInfo = new ProxyInfo();
                        mProxyMap.put(qualifiedName, proxyInfo);
                    }
                    proxyInfo.mInjectElements.put(value, variableElement);
                }
            } else {
                continue;
            }
        }
        //获得被该注解声明的方法
        Set<? extends Element> elementsMethod = roundEnvironment.getElementsAnnotatedWith(TaOnClick.class);
        for (Element element : elementsMethod) {
            if (element.getKind() == ElementKind.METHOD) {
                //获取注解的值
                TaOnClick onClick = element.getAnnotation(TaOnClick.class);
                if (onClick != null) {
                    int[] value = onClick.value();
                    if (value != null && value.length > 0) {
                        for (int i = 0; i < value.length; i++) {
                            ExecutableElement executableElement = (ExecutableElement) element;
                            //这里先要获取上层封装类型，然后强转为TypeElement
                            String qualifiedName = ((TypeElement) element.getEnclosingElement()).getQualifiedName().toString();
                            ProxyInfo proxyInfo = mProxyMap.get(qualifiedName);
                            if (proxyInfo == null) {
                                proxyInfo = new ProxyInfo();
                                mProxyMap.put(qualifiedName, proxyInfo);
                            }
                            proxyInfo.mInjectMethods.put(value[i], executableElement);
                        }
                    }
                }
            } else {
                continue;
            }
        }
    }
    
    private void print(String message) {
        messager.printMessage(Diagnostic.Kind.NOTE, message);
    }
    
    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
