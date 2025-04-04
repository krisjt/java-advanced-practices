module client {
    requires api;
    requires kappa.provider;
    requires java.desktop;
    uses org.example.api.AnalysisService;
}