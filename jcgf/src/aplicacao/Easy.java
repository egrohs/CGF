package aplicacao;

import org.easyway.objects.texture.Texture;
import org.easyway.system.Core;

import cgf.Constantes.Naipes;
import cgf.Constantes.Valores;
import cgf.estado.CartaBaralho;
import examples.example08.ClickableSprite;

public class Easy extends Core {
	public static void main(String[] args) {
		new Easy();
	}

	@Override
	public void creation() {
		new CartaBaralho(Valores.AS, Naipes.ESPADAS);
		// load a non-transparent image
		Texture image = new Texture("/Baralhos/s1.gif");
		// create and show a new sprite with the loaded image
		new ClickableSprite(10, 0, image);

		// load an image and set to put the WHITE color as transparent color
		// Texture imageWithAlpha = new Texture("/images/nave.PNG",255,255,255);
		// create the transparent color
		// new Sprite( 10, 100, imageWithAlpha );
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loop() {
		// TODO Auto-generated method stub
	}
}
