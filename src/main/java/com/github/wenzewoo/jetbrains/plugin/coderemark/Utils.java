/*
 * MIT License
 *
 * Copyright (c) 2021 吴汶泽 <wenzewoo@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.wenzewoo.jetbrains.plugin.coderemark;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String hashMD5(final String input) {
        if (input == null || input.length() == 0) return null;
        try {
            final MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            final byte[] byteArray = md5.digest();
            final BigInteger bigInt = new BigInteger(1, byteArray);
            final StringBuilder result = new StringBuilder(bigInt.toString(16));
            while (result.length() < 32) result.insert(0, "0");
            return result.toString().toUpperCase();
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return input;
    }

    public static String lineText(final Editor editor, final int lineNumber) {
        if (null == editor) return null;

        final int startOffset = editor.getDocument().getLineStartOffset(lineNumber);
        final int endOffset = editor.getDocument().getLineEndOffset(lineNumber);
        return editor.getDocument().getText(new TextRange(startOffset, endOffset));
    }

    public static Integer lineNumber(final Editor editor) {
        if (null == editor) return null;

        return editor.getDocument().getLineNumber(editor.getCaretModel().getOffset());
    }

    public static String filePath(final Editor editor) {

        if (editor instanceof EditorEx) {

            final VirtualFile virtualFile = ((EditorEx) editor).getVirtualFile();
            if (null == virtualFile) return "";

            return virtualFile.getCanonicalPath();
        }
        return "";
    }

    public static boolean isEmpty(final String text) {
        return null == text || "".equals(text.trim());
    }

    public static boolean isNotEmpty(final String text) {
        return !isEmpty(text);
    }

    public static String maxLength(final String text, final int length) {
        if (isEmpty(text)) return text;
        if (text.length() <= length) return text;

        return String.format("%s...", text.substring(0, length - 1));
    }

    public static boolean endsWith(final String text, final String suffix) {
        if (isEmpty(text)) return false;

        return text.endsWith(suffix);
    }

    public static boolean startsWith(final String text, final String prefix) {
        if (isEmpty(text)) return false;

        return text.startsWith(prefix);
    }
}
