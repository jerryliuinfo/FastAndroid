package apache.artemis_compiler.proxy.util;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * 类描述：
 * 创建人：yifei
 * 创建时间：2018/5/10
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class Logger {
    private Messager msg;

    public Logger(Messager messager) {
        msg = messager;
    }

    /**
     * Print info log.
     */
    public void info(CharSequence info) {
        msg.printMessage(Diagnostic.Kind.NOTE, info);
        System.out.println(info.toString());
        java.util.logging.Logger.getLogger("info").info(info.toString());
    }

    public void error(CharSequence error) {
        msg.printMessage(Diagnostic.Kind.ERROR, "An exception is encountered, [" + error + "]");
    }

    public void error(Throwable error) {
        msg.printMessage(Diagnostic.Kind.ERROR, "An exception is encountered, [" + error.getMessage() + "]" + "\n" + formatStackTrace(error.getStackTrace()));
    }

    public void warning(CharSequence warning) {
        msg.printMessage(Diagnostic.Kind.WARNING,  warning);
    }

    private String formatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append("    at ").append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
