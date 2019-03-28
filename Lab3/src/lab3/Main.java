package lab3;

import lab3.controller.Controller;
import lab3.model.Model;
import lab3.view.View;

public class Main {
	public static void main(String[] args) {
		new Controller(new Model(), new View());
	}
}
