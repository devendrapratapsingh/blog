package com.uniquenotion.health;

import java.io.File;
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

@WebServlet(urlPatterns = { "/health" })
public class HealthCheckServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger("healthcheck");
	private static final String DISABLED_STRING = "DISABLED";

	private boolean isMaintenanceActive(final HttpServletResponse response) throws ServletException {
		final String maintenanceFilePath = "/opt/maintenance/";
		try {
			if (maintenanceFilePath != null) {
				File file = new File(maintenanceFilePath);
				if (!file.isDirectory() && file.exists()) {
					response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
					response.getWriter().append(DISABLED_STRING);
					LOGGER.warn("Application Server Node is in {} mode", DISABLED_STRING);
					return true;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error in isMaintenanceOn {}", e);
		}

		return false;
	}

	private void getVersion(final HttpServletResponse response) throws ServletException {
		try {
			ServletContext application = getServletConfig().getServletContext();
			try (InputStream inputStream = application.getResourceAsStream("/META-INF/MANIFEST.MF")) {
				Manifest manifest = new Manifest(inputStream);
				response.getWriter()
						.print("Application Name " + manifest.getMainAttributes().getValue("Implementation-Version"));
			}
		} catch (Exception e) {
			LOGGER.error("Error in servlet {}", e);
		}

	}

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
		if (!isMaintenanceActive(response)) {
			getVersion(response);
		}
	}

}
