package br.com.jeanbarcellos.demo004;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class LifecycleLogger {

    @EventListener
    void onStarting(org.springframework.boot.context.event.ApplicationStartingEvent e) {
        log("STARTING");
    }

    @EventListener
    void onPrepared(org.springframework.boot.context.event.ApplicationPreparedEvent e) {
        log("PREPARED (context criado, beans não)");
    }

    @EventListener
    void onStarted(org.springframework.boot.context.event.ApplicationStartedEvent e) {
        log("STARTED (beans prontos, servidor subindo)");
    }

    @EventListener
    void onReady(org.springframework.boot.context.event.ApplicationReadyEvent e) {
        log("READY (aceitando requests)");
    }

    void log(String msg) {
        System.out.println(">>> LIFECYCLE: " + msg);
    }
}