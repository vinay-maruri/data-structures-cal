package lab14;

import lab14lib.*;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		Generator generator = new SineWaveGenerator(200);
		GeneratorPlayer gp = new GeneratorPlayer(generator);
		gp.play(1000000);
		Generator generator0 = new SineWaveGenerator(200);
		GeneratorDrawer gd = new GeneratorDrawer(generator0);
		gd.draw(4096);
		Generator generator1 = new SineWaveGenerator(200);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator1);
		gav.drawAndPlay(4096, 1000000);
		Generator g1 = new SineWaveGenerator(200);
		Generator g2 = new SineWaveGenerator(201);
		ArrayList<Generator> generators = new ArrayList<Generator>();
		generators.add(g1);
		generators.add(g2);
		MultiGenerator mg = new MultiGenerator(generators);
		GeneratorAudioVisualizer gav1 = new GeneratorAudioVisualizer(mg);
		gav1.drawAndPlay(500000, 1000000);
		Generator generator2 = new SawToothGenerator(512);
		GeneratorAudioVisualizer gav2 = new GeneratorAudioVisualizer(generator2);
		gav2.drawAndPlay(4096, 1000000);
		Generator generator3 = new AcceleratingSawToothGenerator(200, 1.1);
		GeneratorAudioVisualizer gav3 = new GeneratorAudioVisualizer(generator3);
		gav3.drawAndPlay(4096, 1000000);
		Generator generator4 = new StrangeBitwiseGenerator(512);
		GeneratorAudioVisualizer gav4 = new GeneratorAudioVisualizer(generator4);
		gav4.drawAndPlay(4096, 1000000);
	}
} 