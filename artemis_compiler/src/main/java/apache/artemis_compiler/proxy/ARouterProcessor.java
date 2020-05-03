package apache.artemis_compiler.proxy;

import com.apache.artemis_annotation.BindPath;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by Jerry on 2020-04-30.
 * 必须加上这个@AutoService(Processor.class)
 */
@AutoService(Processor.class)
public class ARouterProcessor extends ABaseProcessor {
    @Override
    public Class[] getgetSupportedAnnotationTypesName() {
        return new Class[]{BindPath.class};
    }

    public static final String PROXY = "$$APTPROXY";


    //proxytool.IProxy
    public static final ClassName IPROXY = ClassName.get("com.tesla.framework.component.router", "IRouter");

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        getLogger().info("ARouterProcessor process");
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        if (elements != null){
            for (Element element : elements) {
                //只能修饰类
                if (!(element instanceof TypeElement)){
                    continue;
                }
                TypeElement typeElement = (TypeElement) element;

                //生成public void putActivity()方法
                MethodSpec.Builder injectMethodBuilder = MethodSpec.methodBuilder("putActivity")
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(Override.class)
                        .addParameter(TypeName.get(element.asType()), "target", Modifier.FINAL)

                        ;

                MethodSpec injectMethodSpec = injectMethodBuilder.build();

                String className = element.getSimpleName() +"$$Proxy";

                TypeSpec typeSpec = TypeSpec.classBuilder(className)
                        .addModifiers(Modifier.PUBLIC,Modifier.FINAL)
                        .addSuperinterface(ParameterizedTypeName.get(IPROXY, TypeName.get(element.asType())))
                        .addMethod(injectMethodSpec)
                        .build();
                String packageName = "com.apache.aroute.apt";
                getLogger().info("ARouterProcessor process packageName:"+packageName);

                JavaFile file = JavaFile.builder(packageName, typeSpec)
                        .addFileComment(" This codes are generated automatically. Do not modify!")
                        .build();
                try {
                    file.writeTo(getFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

}
