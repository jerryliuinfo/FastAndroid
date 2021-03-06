package apache.artemis_compiler.proxy;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by cry on 2016/9/21.
 */

public class ProxyClass {

    /**
     * 类元素
     */
    public TypeElement mTypeElement;
    /**
     * 元素相关的辅助类
     */
    private Elements mElementUtils;
    /**
     * FieldViewBinding类型的集合
     */
    private Set<FieldViewBinding> bindViews = new HashSet<>();
    public Set<MethodViewBinding> mMethods = new HashSet<>();

    public ProxyClass(TypeElement mTypeElement, Elements mElementUtils) {
        this.mTypeElement = mTypeElement;
        this.mElementUtils = mElementUtils;
    }

    public void add(FieldViewBinding bindView) {
        bindViews.add(bindView);
    }

    public void add(MethodViewBinding methodViewBinding) {
        mMethods.add(methodViewBinding);
    }

    //proxytool.IProxy
    public static final ClassName IPROXY = ClassName.get("com.tesla.framework.support.annotation", "IProxy");
    //android.view.View
    public static final ClassName VIEW = ClassName.get("android.view", "View");
    //android.view.View.OnClickListener
    public static final ClassName VIEW_ON_CLICK_LISTENER = ClassName.get("android.view", "View", "OnClickListener");
    //生成代理类的后缀名
    public static final String SUFFIX = "$$Proxy";

  /*  public class MainActivity$$Proxy implements IProxy<MainActivity> {
        @Override
        public void inject(final MainActivity target, View root) {
            target.btnOne = (Button)(root.findViewById(2131427413));
            target.btnTwo = (Button)(root.findViewById(2131427414));
            View.OnClickListener listener;
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    target.myClick(view);
                }
            } ;
            (root.findViewById(2131427413)).setOnClickListener(listener);
            (root.findViewById(2131427414)).setOnClickListener(listener);
        }
    }*/



    /**
     * 用于生成代理类
     */
    public JavaFile generateProxy() {

        //生成public void inject(final T target, View root)方法
        MethodSpec.Builder injectMethodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mTypeElement.asType()), "target", Modifier.FINAL)
                .addParameter(VIEW, "root");

        System.out.println("bindViews.size() = "+ bindViews.size() );

        //在inject方法中，添加我们的findViewById逻辑
        for (FieldViewBinding model : bindViews) {
            // find views
            injectMethodBuilder.addStatement("target.$N = ($T)(root.findViewById($L))", model.getVariableName(),
                    ClassName.get(model.getTypeMirror()), model.getResId());
            System.out.println("inject views field name = "+ model.getVariableName() +", field type = "+ClassName.get(model.getTypeMirror())
                    +", resId = "+ model.getResId());
        }

        System.out.println("mMethods.size() = "+ mMethods.size() );
        if (mMethods.size() > 0) {
           //生成 View.OnClickListener listener;
            injectMethodBuilder.addStatement("$T listener", VIEW_ON_CLICK_LISTENER);
        }


        for (MethodViewBinding method : mMethods) {
            // declare OnClickListener anonymous class
            TypeSpec listener = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(VIEW_ON_CLICK_LISTENER)
                    .addMethod(MethodSpec.methodBuilder("onClick")
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .returns(TypeName.VOID)
                            .addParameter(VIEW, "view")
                            // target.myClick(view);
                            .addStatement("target.$N($L)", method.getMethodName(), method.isParameterEixt() ? method.getParameterName() : "")
                            .build())
                    .build();
            injectMethodBuilder.addStatement("listener = $L ", listener);
            for (int id : method.getIds()) {
                // set listeners
                //生成 (root.findViewById(2131427414)).setOnClickListener(listener);
                injectMethodBuilder.addStatement("(root.findViewById($L)).setOnClickListener(listener)", id);
            }
        }


        // 添加以$$Proxy为后缀的类
        TypeSpec finderClass = TypeSpec.classBuilder(mTypeElement.getSimpleName() + SUFFIX)
                .addModifiers(Modifier.PUBLIC)
                //添加父接口
                .addSuperinterface(ParameterizedTypeName.get(IPROXY, TypeName.get(mTypeElement.asType())))
                .addMethod(injectMethodBuilder.build())
                .build();

        //添加包名
        String packageName = mElementUtils.getPackageOf(mTypeElement).getQualifiedName().toString();

        //生成file文件
        return JavaFile.builder(packageName, finderClass).build();
    }
}
