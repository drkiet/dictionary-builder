package com.drkiet.vertx.dictbuilder;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

/**
 * To run vertx,
 * 
 * <code>
 * Run as a Java app with this main class: io.vertx.core.Launcher.
 * Program arguments are: run com.drkiet.vertx.dictbuilder.MainVerticle
 * 
 * Maven build: mvn clean package.
 * Run: java -jar target\dictbuilder-1.0.0-SNAPSHOT-fat.jar 
 * 				run com.drkiet.vertx.dictbuilder.MainVerticle
 * </code>
 * 
 * @author ktran
 *
 */
public class MainVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		vertx.createHttpServer().requestHandler(req -> {
			req.response().putHeader("content-type", "text/plain").end("Hello from Vert.x!");
		}).listen(8181, http -> {
			if (http.succeeded()) {
				startFuture.complete();
				System.out.println("HTTP server started on http://localhost:8181");
			} else {
				startFuture.fail(http.cause());
			}
		});
	}

}
