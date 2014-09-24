package edu.cmu.hw1.chongshm;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.hw1chongshm.types;

public class Gene_Name_Annotator extends JCasAnnotator_ImplBase {
	/**@author machongshen
	 * This annotator that discovers Gene Tag and Name in the file by using the Stanford-nlp API.
	 * This code first creates the new PosTagNamedEntityRecognizer class, then analyzes each of the 
	 * arguments using the stanford nlp. And also save the types of Gene name tag to the CAS. 
	 * 
	 * @return void no need to return.
	 */
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		// TODO Auto-generated method stub
		String docText = aJCas.getDocumentText();
			String[] k = docText.split(" ", 2);
			PosTagNamedEntityRecognizer a;
			try {
				a = new PosTagNamedEntityRecognizer();
				Map<Integer, Integer> m = a.getGeneSpans(k[1]);
				Iterator iter = m.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					String extract = k[1].substring((Integer) key,
							(Integer) val);
					types types = new types(aJCas);
					types.setGene_Sign(k[0]);
					types.setGene_Mark(extract);
					types.setStart((Integer) key);
					types.setEnd((Integer) val);
					types.addToIndexes();
					
				}

			} catch (ResourceInitializationException e) {

			}

			
		}

	}


