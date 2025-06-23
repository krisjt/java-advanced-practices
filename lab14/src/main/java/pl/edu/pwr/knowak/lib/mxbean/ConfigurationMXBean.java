package pl.edu.pwr.knowak.lib.mxbean;

public interface ConfigurationMXBean {
    void configurePriorities();
    void configureStationCategories(int id);
    String getConfigurationStatus();
    int getCategoriesCount();
    int getStationsCount();
    void configureNumberOfCategories(int numberOfCategories);
    int getNumberOfCategories();
}
