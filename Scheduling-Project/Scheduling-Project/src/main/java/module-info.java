module com.example.schedulingproject {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                    requires org.kordamp.ikonli.javafx;
                
    opens com.example.schedulingproject to javafx.fxml;
    exports com.example.schedulingproject;
}