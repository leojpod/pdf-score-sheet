
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.leojpod.demo.pdfscoresheet.PDFScoreSheetServlet;

@SuppressWarnings("serial")
public class SetupServer extends HttpServlet {

    public static void main(String[] args) throws Exception{
    	int port;
    	try {
    		port = Integer.valueOf(System.getenv("PORT"));
    	} catch (NumberFormatException ex) {
    		port = 5000;
    	}
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(new ServletHolder(new PDFScoreSheetServlet()),"/api/generate.pdf");
        ResourceHandler staticResources = new ResourceHandler();
        staticResources.setWelcomeFiles(new String[]{"index.html"});
        staticResources.setResourceBase(".");
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{staticResources, context, new DefaultHandler()});
        server.setHandler(handlers);
        server.start();
        server.join();
    }
}
