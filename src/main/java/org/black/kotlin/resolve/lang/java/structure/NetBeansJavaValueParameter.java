package org.black.kotlin.resolve.lang.java.structure;

import com.google.common.collect.Lists;
import static org.black.kotlin.resolve.lang.java.structure.NetBeansJavaElementFactory.annotations;

import java.util.Collection;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/**
 *
 * @author Александр
 */
public class NetBeansJavaValueParameter extends NetBeansJavaElement<VariableElement> implements JavaValueParameter{

    private final String name;
    private final boolean isVararg;
    
    public NetBeansJavaValueParameter(VariableElement type, String name, boolean isVararg){
        super(type);
        this.name = name;
        this.isVararg = isVararg;
    }
    
    @Override
    public Name getName() {
        return Name.identifier(name);
    }

    @Override
    public JavaType getType() {
        return NetBeansJavaType.create(getBinding().asType());
    }

    @Override
    public boolean isVararg() {
        return isVararg;
    }

    @Override
    public Collection<JavaAnnotation> getAnnotations() {
        List<Element> annotations = Lists.newArrayList();
        for (AnnotationMirror elem : getBinding().getAnnotationMirrors()){
            annotations.add(elem.getAnnotationType().asElement());// maybe incorrect, to fix in future
        }
        return annotations(annotations.toArray(new Element[annotations.size()]));
    }

    @Override
    public JavaAnnotation findAnnotation(FqName fqName) {
        return NetBeansJavaElementUtil.findAnnotation(getBinding().asType(), fqName);
    }

    @Override
    public boolean isDeprecatedInJavaDoc() {
        return false;//temporary
    }
    
}
