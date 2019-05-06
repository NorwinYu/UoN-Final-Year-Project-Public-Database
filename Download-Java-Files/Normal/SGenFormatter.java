/** 
 * Copyright (c) 2015 committers of YAKINDU and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * Contributors:
 * committers of YAKINDU - initial API and implementation
 *
*/
package org.yakindu.sct.generator.genmodel.formatting;

import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.eclipse.xtext.util.Pair;
import org.yakindu.sct.generator.genmodel.services.SGenGrammarAccess;

public class SGenFormatter extends AbstractDeclarativeFormatter {

	@Override
	protected void configureFormatting(FormattingConfig c) {
		SGenGrammarAccess g = (SGenGrammarAccess) getGrammarAccess();
		// It's usually a good idea to activate the following three statements.
		// They will add and preserve newlines around comments
		c.setLinewrap(0, 1, 2).before(g.getSL_COMMENTRule());
		c.setLinewrap(0, 1, 2).before(g.getML_COMMENTRule());
		c.setLinewrap(0, 1, 1).after(g.getML_COMMENTRule());
		c.setLinewrap().before(g.getPropertyDefinitionRule());

		// Find elements which declare their body in curly brackets.
		// - Increment Indentation for the body.
		// - Line wrap before opening and after closing element
		for (Pair<Keyword, Keyword> pair : grammar.findKeywordPairs("{", "}")) {
			c.setIndentation(pair.getFirst(), pair.getSecond());
			c.setLinewrap().after(pair.getFirst());
			c.setLinewrap().around(pair.getSecond());

			Keyword openingBrace = pair.getFirst();
			Group containingGroup = (Group) openingBrace.eContainer();

			c.setLinewrap(1, 2, 2).before(containingGroup);
			c.setLinewrap(1, 1, 2).after(containingGroup);
		}

		c.setLinewrap().around(g.getFeatureConfigurationRule());
		c.setLinewrap().around(g.getFeatureParameterValueRule());
		c.setNoLinewrap().after(g.getGeneratorEntryAccess().getContentTypeAssignment_0());
		c.setNoLinewrap().after(g.getFeatureConfigurationAccess().getFeatureKeyword_1());
		c.setNoLinewrap().before(g.getGeneratorModelAccess().getGeneratorModelKeyword_0());

	}
}