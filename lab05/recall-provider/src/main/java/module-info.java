module recall.provider {
    requires api;
    provides org.example.api.AnalysisService with org.example.recall.provider.Recall;
}