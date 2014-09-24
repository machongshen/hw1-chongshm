package edu.cmu.hw1.chongshm;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceProcessException;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.Chunking;
import com.aliasi.chunk.ConfidenceChunker;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Strings;

import edu.cmu.hw1chongshm.types;

public class RunChunker_Annotator extends JCasAnnotator_ImplBase {

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		// TODO Auto-generated method stub
		// JCas jcas = null;
		Chunker chunker = null;
		File modelFile = new File(
				"./src/main/resources/data/ne-en-bio-genetag.hmmchunker");
		String docText = aJCas.getDocumentText();
		String[] k = docText.split(" ", 2);
		try {
			chunker = (Chunker) AbstractExternalizable.readObject(modelFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Chunking chunking = chunker.chunk(k[1]);
		Set<Chunk> chunkSet = chunking.chunkSet();
		Iterator<Chunk> it = chunkSet.iterator();
		while (it.hasNext()) {
			types types = new types(aJCas);
			Chunk chunk = it.next();
			int start = chunk.start();
			int end = chunk.end();
			String text = k[1].substring(start, end);
			types.setGene_Sign(k[0]);
			types.setGene_Mark(text);
			types.setStart(start);
			types.setEnd(end);
			types.addToIndexes();

		}
	}
}