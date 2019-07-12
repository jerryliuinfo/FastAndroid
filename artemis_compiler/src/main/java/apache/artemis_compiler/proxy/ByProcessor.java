package apache.artemis_compiler.proxy;

import com.apache.artemis_annotation.ByView;
import com.apache.artemis_annotation.DIActivity;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
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
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * author: 01370340
 * data: 2019/6/27
 * description:
 */
@AutoService(Processor.class)
//指定编译的Java版本
@SupportedSourceVersion(SourceVersion.RELEASE_7)
//指定处理的注解
@SupportedAnnotationTypes({"com.apache.artemis_annotation.DIActivity"})
public class ByProcessor extends AbstractProcessor {

    public static final String Activity = "android.app.Activity";
    
    private Filer mFiler;
    
    private Elements mElementUtils;

    private Types mTypes;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        mFiler = processingEnvironment.getFiler();
        
        mElementUtils = processingEnvironment.getElementUtils();

        mTypes = processingEnvironment.getTypeUtils();
    }

    /*public final class ManagerFindByMainActivity {
        public static void findById(MainActivity activity) {
            activity.textView = (android.widget.TextView) activity.findViewById(2131165320);
            activity.textView1 = (android.widget.TextView) activity.findViewById(2131165321);
            activity.textView2 = (android.widget.TextView) activity.findViewById(2131165322);
            activity.button = (android.widget.Button) activity.findViewById(2131165218);
        }
    }
*/

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(DIActivity.class);
        if (elements != null){
            //判断注解的节点是否为Activity
            TypeElement activityTypeElement = mElementUtils.getTypeElement(Activity);
            for (Element element : elements) {
                //类
                TypeMirror typeMirror =  element.asType();

                //判断注解的节点是否为Activity
                if (mTypes.isSubtype(typeMirror, activityTypeElement.asType())){
                    TypeElement typeElement = (TypeElement) element;

                    //MainActivity activity
                    ParameterSpec parameterSpec = ParameterSpec.builder(ClassName.get(typeMirror), "activity").build();

                    //public static void findById
                    MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("findViewById")
                            .addModifiers(Modifier.PUBLIC,Modifier.STATIC).returns(TypeName.VOID)
                            .addParameter(parameterSpec)
                            ;

                    /*List<? extends Element> allMembers =  mElementUtils.getAllMembers(typeElement);
                    for (Element member : allMembers) {
                        ByView byView = member.getAnnotation(ByView.class);
                        if (byView == null){
                            continue;
                        }

                        methodBuilder.addStatement(String.format("activity.%s = (%s)activity.findViewById(%s)", member.getSimpleName(),
                                ClassName.get(member.asType()).toString(), byView.value()));
                    }*/

                    // activity.textView = (android.widget.TextView) activity.findViewById(2131165320);

                    //获取所有用 ByView 注解的成员变量元素
                    Set<? extends Element> byViewElements = roundEnvironment.getElementsAnnotatedWith(ByView.class);
                    for (Element byViewElement : byViewElements) {

                        ByView byView = byViewElement.getAnnotation(ByView.class);
                        methodBuilder.addStatement(String.format("activity.%s = (%s)activity.findViewById(%s)", byViewElement.getSimpleName(),
                                ClassName.get(byViewElement.asType()).toString(), byView.value()));
                    }

                    String className = element.getSimpleName() +"$$Proxy";
                    //定义类  public final class 类名+ $$Proxy
                    TypeSpec typeSpec = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC,Modifier.FINAL)
                            .addMethod(methodBuilder.build())
                            .build();

                    JavaFile javaFile = JavaFile.builder(getPackageName(typeElement), typeSpec).build();
                    try {
                        javaFile.writeTo(mFiler);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        return true;
    }


    private String getPackageName(Element typeElement){
        return mElementUtils.getPackageOf(typeElement).getQualifiedName().toString();
    }

}
