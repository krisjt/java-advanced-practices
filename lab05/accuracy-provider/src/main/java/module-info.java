module accuracy.provider {
    requires api;
    provides org.example.api.AnalysisService with org.example.accuracy.provider.Accuracy;
}