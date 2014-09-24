package edu.cmu.hw1.chongshm;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.XMLSerializer;
import org.xml.sax.SAXException;

/**
 * A simple CAS consumer that writes the CAS to a output file.
 * <p>
 * This CAS Consumer takes one parameter:
 * <ul>
 * <li><code>OutputDirectory</code> - path to directory into which output files
 * will be written</li>
 * </ul>
 */
public class XmiWriterCasConsumer extends CasConsumer_ImplBase {
	/**
	 * Name of configuration parameter that must be set to the path of a
	 * directory into which the output files will be written.
	 */
	public static final String PARAM_OUTPUTDIR = "./src/main/resources/data/data_out/hw1-chongshm.out";
	/**
	 * We need to initialize the file we what to save the processed data. Make the file clean.  
	 */
	public void initialize() throws ResourceInitializationException {

		File f = new File(PARAM_OUTPUTDIR);
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			fw.write("");
		fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**@author machongshen
	 * Processes the CAS which was populated by the TextAnalysisEngines. <br>
	 * In this case, the CAS wrote the Gene Name Entity into the output file.
	 * The output file is listing the Gene tag and name which is generated from the TextAnalysisEngines.
	 * 
	 * @param aCAS
	 *            a CAS which has been populated by the TAEs
	 * 
	 * 
	 * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(org.apache.uima.cas.CAS)
	 */
	public void processCas(CAS aCAS) throws ResourceProcessException {
		
		JCas jcas;
		try {
			jcas = aCAS.getJCas();
		} catch (CASException e) {
			throw new ResourceProcessException(e);
		}
		String docText = jcas.getDocumentText();
		String[] k = docText.split(" ", 2);
		try {

			FileWriter fw = new FileWriter(
					PARAM_OUTPUTDIR,true);
			
			BufferedWriter output = new BufferedWriter(fw);
			FSIterator it = jcas.getAnnotationIndex(
					edu.cmu.hw1chongshm.types.type).iterator();
			char [] abc = k[1].toCharArray();
			String Gene_Sign = "";
			String Gene_Mark = "";
			int start = 0;
			int end = 0;
			int count= 0;
			int count1=0;
			
			if (it.hasNext()) {
				edu.cmu.hw1chongshm.types annotation = (edu.cmu.hw1chongshm.types) it
						.next();
				Gene_Sign = annotation.getGene_Sign();
				Gene_Mark = annotation.getGene_Mark();
				/**
				 * This two "for" intend to get the accurate "start" and "end" position.
				 * With 2 count, I could use it to calculate the numbers of all spaces.
				 *  
				 */
			for(int i = 0; i < annotation.getStart(); i++ )
				{
					if (abc[i] == ' ' )
					{
					 count ++;	
					}
					
				}
			for(int i = annotation.getStart(); i < annotation.getEnd(); i++ )
			{
				if (abc[i] == ' ' )
				{
				 count1 ++;	
				}
				
			}
				System.out.println(count);
				start = annotation.getStart()-count;
				end = annotation.getEnd()-count-count1-1;
				output.append(Gene_Sign + "|" + start + " " + end + "|"
						+ Gene_Mark);
				output.newLine();
				output.flush();

			}
			fw.close();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// retrieve the filename of the input file from the CAS

	}

}
