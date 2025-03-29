package pl.edu.pwr.service;
public interface StatusListener {
    /**
     * Metoda słuchacza
     * @param s - status przetwarzania zadania
     */
    void statusChanged(Status s);
}
