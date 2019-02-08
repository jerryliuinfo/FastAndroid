package apache.artemis_compiler.proxy;


import com.apache.artemis_annotation.BindOnClick;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;


/**
 * Created by cry on 2016/9/25.
 */

public class MethodViewBinding {

    private ExecutableElement mElement;//可以表示一个普通方法、构造方法、初始化方法（静态和实例）。
    private String mMethodName;
    private int[] mIds;
    private boolean mParameterEixt;
    private String mParameterName;

    public MethodViewBinding(Element element) throws IllegalArgumentException {
        mElement = (ExecutableElement) element;

        BindOnClick viewById = element.getAnnotation(BindOnClick.class);
        mIds = viewById.value();
        //方法名
        mMethodName = element.getSimpleName().toString();
        List<? extends VariableElement> parameters = mElement.getParameters();
        if (parameters.size() > 1) {  //参数不能超过1个
            throw new IllegalArgumentException(
                    String.format("The method annotated with @%s must less two parameters", BindOnClick.class.getSimpleName()));
        }

        if (parameters.size() == 1) { //如果有参数必须是View类型
            VariableElement variableElement = parameters.get(0);
            if (!variableElement.asType().toString().equals(ProxyClass.VIEW.toString())) {
                throw new IllegalArgumentException(
                        String.format("The method parameter must be %s type", ProxyClass.VIEW.toString()));
            }
            mParameterEixt = true;
            mParameterName=variableElement.getSimpleName().toString();
        }

    }


    public int[] getIds() {
        return mIds;
    }

    public String getMethodName() {
        return mMethodName;
    }

    public boolean isParameterEixt() {
        return mParameterEixt;
    }

    public String getParameterName() {
        return mParameterName;
    }
}
