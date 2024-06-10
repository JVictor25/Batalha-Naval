module ProjetoBatalhaNaval {
	requires javafx.controls;
	requires javafx.fxml;
	
	exports br.ufrn.imd;
	
	
	opens application to javafx.graphics, javafx.fxml;
	opens br.ufrn.imd.controle to javafx.fxml;
}
