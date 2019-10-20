package apache.artemis_compiler.proxy;

import com.apache.artemis_annotation.AptTest;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * author: 01370340
 * data: 2019/6/27
 * description:
 */
@AutoService(Processor.class)
//指定编译的Java版本
@SupportedSourceVersion(SourceVersion.RELEASE_7)
//指定处理的注解
@SupportedAnnotationTypes({"com.apache.artemis_annotation.AptTest"})
public class JavaFileProcessor extends AbstractProcessor {

    private Filer mFiler;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);


        mFiler = processingEnvironment.getFiler();
        System.out.println("JavaFileProcessor init");
    }

    /*public final class HelloWord {
        public static void main(String[] args) {
            System.out.println("Hello JavaPoet");
        }
    }*/

    /**
     *  指定哪些注解应该被注解处理器注册
     */
   /* @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(AptTest.class.getName());
        return types;
    }


    *//**
     *  用来指定你使用的 java 版本
     *//*
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }*/

    public static final String PROXY = "$$APTPROXY";
    //proxytool.IProxy
    public static final ClassName IPROXY = ClassName.get("com.tesla.framework.support.annotation", "IProxy");
    //android.view.View
    public static final ClassName VIEW = ClassName.get("android.view", "View");

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("JavaFileProcessor process");

        MethodSpec methodSpec = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC).returns(TypeName.VOID)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)",System.class, "Hello Javapoet")
                .build();




        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(AptTest.class);
        if (elements != null){
            for (Element element : elements) {
                //只能修饰类
                if (!(element instanceof TypeElement)){
                    continue;
                }
                //TypeElement typeElement = (TypeElement) element.getEnclosingElement();

                //生成public void inject(final T target, View root)方法
                MethodSpec.Builder injectMethodBuilder = MethodSpec.methodBuilder("inject")
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(Override.class)
                        .addParameter(TypeName.get(element.asType()), "target", Modifier.FINAL)
                        .addParameter(VIEW, "root");

                MethodSpec injectMethodSpec = injectMethodBuilder.build();

                TypeSpec typeSpec = TypeSpec.classBuilder("HelloWorld")
                        .addModifiers(Modifier.PUBLIC,Modifier.FINAL)
                        .addSuperinterface(ParameterizedTypeName.get(IPROXY, TypeName.get(element.asType())))
                        .addMethod(methodSpec)
                        .addMethod(injectMethodSpec)
                        .build();

                JavaFile file = JavaFile.builder("com.apache.fastandroid" + PROXY, typeSpec)
                        .addFileComment(" This codes are generated automatically. Do not modify!")
                        .build();
                try {
                    file.writeTo(mFiler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return true;
    }



}
