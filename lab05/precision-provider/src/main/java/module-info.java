module precision.provider {
    requires api;
    provides org.example.api.AnalysisService with org.example.precision.provider.Precision;
}