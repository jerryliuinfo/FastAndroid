package apache.artemis_compiler.proxy;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


import apache.artemis_compiler.proxy.util.Logger;

/**
 * Created by Jerry on 2020-04-30.
 */
abstract public class ABaseProcessor extends AbstractProcessor {
    /**
     * 用来创建新源、类或辅助文件的 Filer。
     */
    private Filer file;
    private Logger logger;
    /**
     * Elements是一个工具类，可以处理相关Element（包括ExecutableElement, PackageElement,
     * TypeElement, TypeParameterElement, VariableElement）
     */
    private Elements mElementUtils; //元素相关的辅助类
    private Messager mMessager; //日志相关的辅助类
    /**
     * 返回用来在类型上进行操作的某些实用工具方法的实现。
     */
    private Types mTypes;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        file = processingEnvironment.getFiler();
        logger = new Logger(processingEnv.getMessager());   // Package the log utils.
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
        mTypes = processingEnvironment.getTypeUtils();
    }

    /**
     * 指定哪些注解应该被注解处理器注册
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        Class[] clzs = getgetSupportedAnnotationTypesName();
        if (clzs != null && clzs.length > 0){
            for (Class clz : clzs) {
                types.add(clz.getCanonicalName());
            }
        }

        return types;
    }

    public abstract Class[] getgetSupportedAnnotationTypesName();


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }

    public Filer getFile() {
        return file;
    }

    public void setFile(Filer file) {
        this.file = file;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Elements getmElementUtils() {
        return mElementUtils;
    }

    public void setmElementUtils(Elements mElementUtils) {
        this.mElementUtils = mElementUtils;
    }

    public Messager getmMessager() {
        return mMessager;
    }

    public void setmMessager(Messager mMessager) {
        this.mMessager = mMessager;
    }

    public Types getTypes() {
        return mTypes;
    }


    public String getPackageName(Element typeElement){
        return getmElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
    }
}
