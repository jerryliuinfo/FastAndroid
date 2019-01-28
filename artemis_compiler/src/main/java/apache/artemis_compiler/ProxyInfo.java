package apache.artemis_compiler;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public class ProxyInfo {

    private TypeElement typeElement;

    private int value;

    private String pkgName;
    /**
     * key为id，也就是成员变量注解的值，value为对应的成员变量
     */
    private Map<Integer,VariableElement> injectElements = new HashMap<>();


    public TypeElement getTypeElement() {
        return typeElement;
    }

    public void setTypeElement(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public Map<Integer, VariableElement> getInjectElements() {
        return injectElements;
    }

    public void setInjectElements(Map<Integer, VariableElement> injectElements) {
        this.injectElements = injectElements;
    }
}
