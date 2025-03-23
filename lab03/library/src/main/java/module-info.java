module library {
    requires java.net.http;
    requires com.google.gson;
    requires org.json;

    opens pl.edu.pwr.apiconnetion.models to com.google.gson;
    exports pl.edu.pwr.service;
    exports pl.edu.pwr.apiconnetion.models;
    exports pl.edu.pwr.apiconnetion;
}