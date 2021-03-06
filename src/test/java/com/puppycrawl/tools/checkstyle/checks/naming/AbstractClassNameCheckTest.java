////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractClassNameCheck
.ILLEGAL_ABSTRACT_CLASS_NAME;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractClassNameCheck
.NO_ABSTRACT_CLASS_MODIFIER;

public class AbstractClassNameCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testIllegalAbstractClassName() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AbstractClassNameCheck.class);
        checkConfig.addAttribute("ignoreName", "false");
        checkConfig.addAttribute("ignoreModifier", "true");

        final String pattern = "^Abstract.+$|^.*Factory$";

        final String[] expected = {
            "3:1: " + getCheckMessage(ILLEGAL_ABSTRACT_CLASS_NAME, "InputAbstractClassName", pattern),
            "6:1: " + getCheckMessage(ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractClassName", pattern),
            "9:1: " + getCheckMessage(ILLEGAL_ABSTRACT_CLASS_NAME, "FactoryWithBadName", pattern),
            "13:5: " + getCheckMessage(ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractInnerClass", pattern),
        };

        verify(checkConfig, getPath("naming" + File.separator + "InputAbstractClassName.java"), expected);
    }

    @Test
    public void testIllegalClassType() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(AbstractClassNameCheck.class);
        checkConfig.addAttribute("ignoreName", "true");
        checkConfig.addAttribute("ignoreModifier", "false");

        final String[] expected = {
            "26:1: " + getCheckMessage(NO_ABSTRACT_CLASS_MODIFIER, "AbstractClass"),
            "29:1: " + getCheckMessage(NO_ABSTRACT_CLASS_MODIFIER, "Class1Factory"),
            "33:5: " + getCheckMessage(NO_ABSTRACT_CLASS_MODIFIER, "AbstractInnerClass"),
            "38:5: " + getCheckMessage(NO_ABSTRACT_CLASS_MODIFIER, "WellNamedFactory"),
        };

        verify(checkConfig, getPath("naming" + File.separator + "InputAbstractClassName.java"), expected);
    }

    @Test
    public void testAllVariants() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(AbstractClassNameCheck.class);
        checkConfig.addAttribute("ignoreName", "false");
        checkConfig.addAttribute("ignoreModifier", "false");

        final String pattern = "^Abstract.+$|^.*Factory$";

        final String[] expected = {
            "3:1: " + getCheckMessage(ILLEGAL_ABSTRACT_CLASS_NAME, "InputAbstractClassName", pattern),
            "6:1: " + getCheckMessage(ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractClassName", pattern),
            "9:1: " + getCheckMessage(ILLEGAL_ABSTRACT_CLASS_NAME, "FactoryWithBadName", pattern),
            "13:5: " + getCheckMessage(ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractInnerClass", pattern),
            "26:1: " + getCheckMessage(NO_ABSTRACT_CLASS_MODIFIER, "AbstractClass"),
            "29:1: " + getCheckMessage(NO_ABSTRACT_CLASS_MODIFIER, "Class1Factory"),
            "33:5: " + getCheckMessage(NO_ABSTRACT_CLASS_MODIFIER, "AbstractInnerClass"),
            "38:5: " + getCheckMessage(NO_ABSTRACT_CLASS_MODIFIER, "WellNamedFactory"),
        };

        verify(checkConfig, getPath("naming" + File.separator + "InputAbstractClassName.java"), expected);
    }

    @Test
    public void testFalsePositive() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(AbstractClassNameCheck.class);
//        checkConfig.addAttribute("ignoreName", "false");
//        checkConfig.addAttribute("ignoreModifier", "false");

        final String[] expected = {
            "9:5: " + getCheckMessage(NO_ABSTRACT_CLASS_MODIFIER, "AbstractClass"),
        };

        verify(checkConfig, getPath("naming" + File.separator
                 + "InputAbstractClassNameFormerFalsePositive.java"), expected);
    }
}
