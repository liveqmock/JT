/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package validation.builtin;

import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.text.Format;
import java.util.Locale;

import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.text.Document;

import validation.Problems;
import validation.Validator;
import validation.conversion.Converter;

/**
 * An enumeration of validator factories for commonly needed forms of validation
 * such as non-empty strings, valid file names and URLs and so forth.
 * <p>
 * Also contains static factory methods for validators which do things like
 * match regexp's and split strings and run another validator over the
 * components.
 * 
 * @author Tim Boudreau
 */
public enum Validators implements Validator<String> {
	/**
	 * Factory for validators which require non-zero length text.
	 */
	REQUIRE_NON_EMPTY_STRING,
	/**
	 * Factory for validators which require a legal filename
	 */
	REQUIRE_VALID_FILENAME,
	/**
	 * Factory for validators which require a valid integer
	 */
	REQUIRE_VALID_INTEGER,
	/**
	 * Factory for validators which require a non-negative number (may be
	 * floating point or int)
	 */
	REQUIRE_NON_NEGATIVE_NUMBER,
	/**
	 * Factory for validators which require a valid number of some sort
	 */
	REQUIRE_VALID_NUMBER,
	/**
	 * Factory for validators which require that strings not be a Java keyword
	 */
	REQUIRE_JAVA_IDENTIFIER,
	/**
	 * Factory for validators that require that strings be a valid hexadecimal
	 * number
	 */
	VALID_HEXADECIMAL_NUMBER,
	/**
	 * Factory for validators that require that strings not contain whitespace
	 */
	NO_WHITESPACE,
	/**
	 * Factory for validators that require that a string represent a file which
	 * exists on disk
	 */
	FILE_MUST_EXIST,
	/**
	 * Factory for validators that require that a string represent a file which
	 * is a file, not a directory
	 */
	FILE_MUST_BE_FILE,
	/**
	 * Factory for validators that require that a string represent a file which
	 * is a directory, not a data file
	 */
	FILE_MUST_BE_DIRECTORY,
	/**
	 * Factory for validators that require that a string represent a valid URL
	 */
	URL_MUST_BE_VALID,

	/**
	 * Factory for validators that check the validity of an IP address (may
	 * contain port info)
	 */
	IP_ADDRESS,

	/**
	 * Factory for validators that check the validity of an host name (may
	 * contain port info)
	 */
	HOST_NAME,
	/**
	 * Factory for validators that check the validity of an IP address or host
	 * name (may contain port info)
	 */
	HOST_NAME_OR_IP_ADDRESS,
	/**
	 * Factory for validators that do not allow strings which start with a digit
	 */
	MAY_NOT_START_WITH_DIGIT,

	/**
	 * Factory for validators that validate standard internet email addresses
	 * (name + @ + valid hostname or ip).
	 * <p>
	 * <b>Note:</b> This validator is not useful for all legal email addresses -
	 * for example, "root" with is a legal email address on a Unix machine. Do
	 * not use this validator where users might legitimately expect to be able
	 * to enter such unqualified email addresses.
	 */
	EMAIL_ADDRESS,

	/**
	 * Factory for validators that require the passed string to be a valid
	 * character set name according to the specification of
	 * <code>java.nio.Charset.forName(String)</code>.
	 */
	CHARACTER_SET_NAME,

	/**
	 * Factory for validators that validate a java package name. Note that this
	 * does not mean the package name actually exists anywhere, just that it
	 * does not contain java keywords.
	 */
	JAVA_PACKAGE_NAME,

	/**
	 * Validator which only passes non-existent file names
	 */
	FILE_MUST_NOT_EXIST, ;

	/**
	 * Get a validator of strings.
	 * 
	 * @param trim
	 *            If true, String.trim() is called before passing the value to
	 *            the actual validator
	 * @return A validator for strings
	 */
	public Validator<String> forString(boolean trim) {
		Validator<String> result;
		switch (this) {
		case REQUIRE_JAVA_IDENTIFIER:
			result = new NotJavaIdentifierValidator();
			break;
		case REQUIRE_NON_EMPTY_STRING:
			result = new EmptyStringIllegalValidator();
			break;
		case REQUIRE_NON_NEGATIVE_NUMBER:
			result = new NonNegativeNumberValidator();
			break;
		case REQUIRE_VALID_FILENAME:
			result = new IllegalCharactersInFileNameValidator();
			break;
		case REQUIRE_VALID_INTEGER:
			result = new IsAnIntegerValidator();
			break;
		case REQUIRE_VALID_NUMBER:
			result = new IsANumberValidator();
			break;
		case VALID_HEXADECIMAL_NUMBER:
			result = new ValidHexadecimalNumberValidator();
			break;
		case NO_WHITESPACE:
			result = new MayNotContainSpacesValidator();
			break;
		case FILE_MUST_BE_DIRECTORY:
			result = new FileValidator(FileValidator.Type.MUST_BE_DIRECTORY);
			break;
		case FILE_MUST_BE_FILE:
			result = new FileValidator(FileValidator.Type.MUST_BE_FILE);
			break;
		case FILE_MUST_EXIST:
			result = new FileValidator(FileValidator.Type.MUST_EXIST);
			break;
		case FILE_MUST_NOT_EXIST:
			result = new FileValidator(FileValidator.Type.MUST_NOT_EXIST);
			break;
		case URL_MUST_BE_VALID:
			result = new UrlValidator();
			break;
		case IP_ADDRESS:
			result = new IpAddressValidator();
			break;
		case HOST_NAME:
			result = new HostNameValidator(true);
			break;
		case HOST_NAME_OR_IP_ADDRESS:
			result = new ValidHostNameOrIPValidator();
			break;
		case MAY_NOT_START_WITH_DIGIT:
			result = new MayNotStartWithDigit();
			break;
		case EMAIL_ADDRESS:
			result = new EmailAddressValidator();
			return result;
		case JAVA_PACKAGE_NAME:
			result = Validators.splitString("\\.", Validators.merge(false, // NOI18N
					Validators.REQUIRE_JAVA_IDENTIFIER));
			return result;
		default:
			throw new AssertionError();
		}
		if (trim) {
			return new TrimStringValidator(result);
		} else {
			return result;
		}
	}

	/**
	 * Get a validator for objects of the requested type. Note that depending on
	 * calls to
	 * 
	 * @param <T>
	 *            The type of object you need a validator for
	 * @param type
	 *            The class of type T
	 * @param trim
	 *            Whether or not the returned validator should trim strings
	 *            before doing anything
	 * @return A validator
	 * @throws IllegalArgumentException
	 *             if no converter of objects of type <code>type</code> to
	 *             strings has been registered
	 */
	public <T> Validator<T> forType(Class<T> type, boolean trim) {
		Converter<String, T> conv = Converter.find(String.class, type);
		if (conv == null) {
			throw new IllegalArgumentException("No converter from " + // NOI18N
					type.getClass().getName() + " to Strings has been " + // NOI18N
					"registered with Converter.register()"); // NOI18N
		}
		return conv.convert(forString(trim));
	}

	/**
	 * Get a validator which operates against Document objects.
	 * 
	 * @param trim
	 *            If true, the text should be trimmed before validation
	 * @return A validator of this enum kind which operates on Documents
	 */
	public Validator<Document> forDocument(boolean trim) {
		return forType(Document.class, trim);
	}

	/**
	 * Get a validator which operates against ComboBoxModel objects.
	 * 
	 * @param trim
	 *            If true, the text should be trimmed before validation
	 * @return A validator of this enum type which operates on ComboBoxModels.
	 */
	public Validator<ComboBoxModel> forComboBoxModel(boolean trim) {
		return forType(ComboBoxModel.class, trim);
	}

	/**
	 * Get a validator which does fails if any of the characters in the passed
	 * char array are contained in the evaluated text
	 * 
	 * @param chars
	 *            The array of illegal characters
	 * @param trim
	 *            If true, the text should be trimmed before validation
	 * @return A validator which will show an error if any of the passed
	 *         characters are in the String it encounters
	 */
	public static Validator<String> disallowChars(char[] chars, boolean trim) {
		DisallowCharactersValidator v = new DisallowCharactersValidator(chars);
		return trim ? new TrimStringValidator(v) : v;
	}

	/**
	 * Get a validator for documents which logically OR's together all of the
	 * passed built-in validators.
	 * 
	 * @param trim
	 *            If true, the text should be trimmed before validation
	 * @param validators
	 *            one or more Validators enum constants
	 * @return A compound validator for documents
	 */
	public static Validator<Document> forDocument(boolean trim,
			Validator<String>... validators) {
		Validator<String> first = merge(validators);
		Converter<String, Document> c = Converter.find(String.class,
				Document.class);
		return c.convert(first);
	}

	/**
	 * Get a validator for strings which logically OR's together all of the
	 * passed built-in validators.
	 * 
	 * @param trim
	 *            If true, the text should be trimmed before validation
	 * @param validators
	 *            one or more Validators enum constants
	 * @return A compound validator for strings
	 */
	public static Validator<String> merge(boolean trim,
			Validator<String>... validators) {
		return trim ? new TrimStringValidator(merge(validators))
				: merge(validators);
	}

	/**
	 * Get a validator for ComboBoxModels which logically OR's together all of
	 * the passed built-in validators.
	 * 
	 * @param trim
	 *            If true, the text should be trimmed before validation
	 * @param validators
	 *            one or more Validators enum constants
	 * @return A compound validator for combo box models
	 */
	public static Validator<ComboBoxModel> forComboBox(boolean trim,
			Validator<String>... validators) {
		return Converter.find(String.class, ComboBoxModel.class).convert(
				merge(validators));
	}

	/**
	 * Get a validator for button models which fails if at least one of the
	 * button models is not selected.
	 * 
	 * @param msg
	 *            The error message if nothing is selected
	 * @return A validator for an array of button models
	 */
	public static Validator<ButtonModel[]> oneButtonMustBeSelected(String msg) {
		return new ButtonMustBeSelectedModel(msg);
	}

	/**
	 * Wrap a String validator in one which first calls String.trim() on the
	 * strings that will be evaluated.
	 * 
	 * @param others
	 *            Another validator of strings
	 * @return A validator which first trims the String being validated, then
	 *         calls the passed one
	 */
	public static Validator<String> trimString(Validator<String>... others) {
		if (others == null) {
			throw new NullPointerException("Null validators passed"); // NOI18N
		}
		if (others.length == 1) {
			return new TrimStringValidator(others[0]);
		} else {
			return new TrimStringValidator(new OrValidator(others));
		}
	}

	/**
	 * Returns a validator which first splits the string to be evaluated
	 * according to the passed regexp, then passes each component of the split
	 * string to the passed validator.
	 * 
	 * @param regexp
	 *            The pattern to use to split the string
	 * @param other
	 *            the validator the returned one should delegate to to validate
	 *            each component of the split string
	 * @return A validator which evaluates each of component of the split string
	 *         using the passed Validator
	 */
	public static Validator<String> splitString(String regexp,
			Validator<String> other) {
		return new SplitStringValidator(regexp, other);
	}

	/**
	 * Get a validator which fails if the text to validate does not match a
	 * passed regular expression.
	 * 
	 * @param regexp
	 *            The regular expression
	 * @param message
	 *            The output message if there is a problem. The message may
	 *            refer to the component name as {0} and the text that has not
	 *            matched as {1} if desired
	 * @param acceptPartialMatches
	 *            if true, will use <code>Matcher.lookingAt()</code> rather than
	 *            <code>Matcher.matches()</code>
	 * @return A validator
	 */
	public static Validator<String> regexp(String regexp, String message,
			boolean acceptPartialMatches) {
		return new RegexpValidator(regexp, message, acceptPartialMatches);
	}

	/**
	 * Create a number validator that uses a specific locale. For the default
	 * locale, use Validators.REQUIRE_VALID_NUMBER. Use this if you specifically
	 * want validation for another locale.
	 * 
	 * @param l
	 *            The locale to use
	 * @return A string validator for numbers
	 */
	public static Validator<String> validNumber(Locale l) {
		return new IsANumberValidator(l);
	}

	/**
	 * Get a validator that uses a specific <code>Format</code> (e.g.
	 * <code>NumberFormat</code>) instance and fails if
	 * <code>fmt.parseObject()</code> throws a <code>ParseException</code>
	 * 
	 * @param fmt
	 *            A <code>java.text.Format</code>
	 * @return A string validator that uses the provided
	 *         <code>NumberFormat</code>
	 */
	public static Validator<String> forFormat(Format fmt) {
		return new FormatValidator(fmt);
	}

	/**
	 * Get a validator which determines if the passed string can be encoded in
	 * the specified encoding. Useful, for example, for validating strings that
	 * are specified to be in a particular encoding (e.g. email addresses and
	 * US-ASCII)
	 * 
	 * @param charsetName
	 *            The name of a character set recognized by
	 *            <code>java.nio.Charset</code>
	 * @return A validator of character set names
	 * @throws UnsupportedCharsetException
	 *             if the character set is unsupported
	 * @throws IllegalCharsetNameException
	 *             if the character set is illegal
	 */
	public static Validator<String> encodableInCharset(String charsetName) {
		return new EncodableInCharsetValidator(charsetName);
	}

	/**
	 * Get a validator that guarantees that a number is within a certain range
	 * (inclusive)
	 * 
	 * @param min
	 *            The minimum value
	 * @param max
	 *            The maximum value
	 * @return A validator for number ranges
	 */
	public static NumberRange numberRange(Number min, Number max) {
		return new NumberRange(min, max);
	}

	public static NumberRangeE numberRangeE(Number min, Number max) {
		return new NumberRangeE(min, max);
	}

	public static NumberRangeER numberRangeER(Number min, Number max) {
		return new NumberRangeER(min, max);
	}

	public static NumberRangeEL numberRangeEL(Number min, Number max) {
		return new NumberRangeEL(min, max);
	}

	public static NumberMin numberMin(Number min) {
		return new NumberMin(min);
	}

	public static NumberMinE numberMinE(Number min) {
		return new NumberMinE(min);
	}

	public static NumberMax numberMax(Number max) {
		return new NumberMax(max);
	}

	public static Validator<String> notNull() {
		return new NotNull();
	}

	public static NumberMaxE numberMaxE(Number max) {
		return new NumberMaxE(max);
	}

	/**
	 * Validator that enforces minimum input length
	 * 
	 * @param length
	 * @return A validator for string lengths
	 */
	public static Validator<String> minLength(int length) {
		return new MinimumLength(length);
	}

	/**
	 * Validator that enforces maximum input length
	 * 
	 * @param length
	 * @return A validator for string lengths
	 */
	public static Validator<String> maxLength(int length) {
		return new MaximumLength(length);
	}

	/**
	 * Logically OR together multiple validators which work against the same
	 * type.
	 * 
	 * @param <T>
	 *            The type of model (Document, String, etc.) you want to work
	 *            with
	 * @param validators
	 *            Any number of validators which should be logically OR'd
	 *            together, merged into a single validator
	 * @return a single validator which delegates to all of the passed ones
	 */
	public static <T> Validator<T> merge(Validator<T>... validators) {
		if (validators == null) {
			throw new NullPointerException();
		}
		if (validators.length == 1) {
			return validators[0];
		}
		return new OrValidator(validators);
	}

	public boolean validate(Problems problems, String compName, String model) {
		return forString(false).validate(problems, compName, model);
	}

	public Validator<String> trim() {
		return forString(true);
	}

}
