package application;

import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.fxml.*;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.stage.*;
import java.io.IOException;

public class MainSceneController 
{
	@FXML
	private Button StudentButton;
	@FXML
	private Button ScheduleButton;
	@FXML
	private Button RunButton;
	@FXML
	private TextArea InfoText;
	@FXML
	private TextArea FinishedText;

	
	public void SetFinishedText(String str)  // for changing the text box text field
	{ 
		FinishedText.appendText("\n" + str + "\n"); 
	}
	 
	
	 
}
