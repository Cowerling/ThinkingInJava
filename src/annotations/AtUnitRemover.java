package annotations;

import atunit.ClassNameFinder;
import javassist.*;
import javassist.expr.*;
import javassist.bytecode.*;
import javassist.bytecode.annotation.*;
import java.io.*;
import java.util.*;
import util.*;

/**
 * Created by cowerling on 16-3-14.
 */
public class AtUnitRemover implements ProcessFiles.Strategy {
    private static boolean remove = false;

    public static void main(String[] args) throws Exception {
        if(args.length > 0 && args[0].equals("-r")) {
            remove = true;
            String[] nargs = new String[args.length - 1];
            System.arraycopy(args, 1, nargs, 0, nargs.length);
            args = nargs;
        }

        new ProcessFiles(new AtUnitRemover(), "class").start(args);
    }

    public void process(File file) {
        boolean modified = false;
        try {
            String className = ClassNameFinder.thisClass(BinaryFile.read(file));
            if(!className.contains("."))
                return;

            System.out.println(className);
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.get(className);
            for(CtMethod method : ctClass.getDeclaredMethods()) {
                MethodInfo methodInfo = method.getMethodInfo();
                AnnotationsAttribute annotationsAttribute = (AnnotationsAttribute)methodInfo.getAttribute(AnnotationsAttribute.visibleTag);
                if(annotationsAttribute == null)
                    continue;;

                for(Annotation annotation : annotationsAttribute.getAnnotations()) {
                    if(annotation.getTypeName().startsWith("atunit")) {
                        System.out.println(ctClass.getName() + " Method: " + methodInfo.getName() + " " + annotation);
                        if(remove) {
                            ctClass.removeMethod(method);
                            modified = true;
                        }
                    }
                }
            }

            if(modified)
                ctClass.toBytecode(new DataOutputStream(new FileOutputStream(file)));

            ctClass.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
