module kappa.provider {
    requires api;
    provides org.example.api.AnalysisService with org.example.kappa.provider.KappaFactor;
}