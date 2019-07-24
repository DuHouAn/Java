package code_08_annotation.code_02;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("code_08_annotation.code_02.InjectPrint")
public class InjectPrintProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<Element> set=( Set<Element>)roundEnv.getElementsAnnotatedWith(InjectPrint.class);
        for(Element element:set){
            if(element.getKind()== ElementKind.METHOD){
                ExecutableElement executableElement = (ExecutableElement)element;
                //打印方法名
                System.out.println(executableElement.getSimpleName());
                //打印方法的返回类型
                System.out.println(executableElement.getReturnType().toString());
                //获取方法所有的参数
                List<? extends VariableElement> params = executableElement.getParameters();
                for(VariableElement variableElement : params){//遍历并打印参数名
                    System.out.println(variableElement.getSimpleName());
                }
                //打印注解的值
                System.out.println("AnnotationValue:"+executableElement.getAnnotation(InjectPrint.class).value());
            }
        }
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
