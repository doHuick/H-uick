package com.dohit.huick.global.config;

import org.apache.commons.text.StringEscapeUtils;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;

public class HtmlCharacterEscapes extends CharacterEscapes {
	private final int[] asciiEscapes;

	public HtmlCharacterEscapes() {
		// xss 방지 처리할 특수 문자 지정
		asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
		asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
		asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
		asciiEscapes['\"'] = CharacterEscapes.ESCAPE_CUSTOM;
		asciiEscapes['('] = CharacterEscapes.ESCAPE_CUSTOM;
		asciiEscapes[')'] = CharacterEscapes.ESCAPE_CUSTOM;
		asciiEscapes['#'] = CharacterEscapes.ESCAPE_CUSTOM;
		asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;
	}

	@Override
	public int[] getEscapeCodesForAscii() {
		return asciiEscapes;
	}

	@Override
	public SerializableString getEscapeSequence(int ch) {
		char charAt = (char)ch;
		if (Character.isHighSurrogate(charAt) || Character.isLowSurrogate(charAt)) {
			StringBuilder sb = new StringBuilder();
			sb.append("\\u");
			sb.append(String.format("%04x", ch));
			return new SerializedString(sb.toString());
		} else {
			return new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString((char)ch)));
		}
	}
}
