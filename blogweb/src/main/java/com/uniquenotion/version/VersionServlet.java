package com.uniquenotion.version;

import java.io.InputStream;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = { "/version" })
public class VersionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(VersionServlet.class);

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
		getVersion(response);

	}

	private void getVersion(final HttpServletResponse response) throws ServletException {
		try {
			final ServletContext application = getServletConfig().getServletContext();
			try (InputStream inputStream = application.getResourceAsStream("/META-INF/MANIFEST.MF")) {
				final Manifest manifest = new Manifest(inputStream);
				response.getWriter()
						.print("Application Name " + manifest.getMainAttributes().getValue("Implementation-Version"));
			}
		} catch (Exception e) {
			LOGGER.error("Error in servlet {}", e);
		}
	}

}
