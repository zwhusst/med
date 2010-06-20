package com.souyibao.shared.util;

import java.io.*;

import org.w3c.dom.*;

/**
 * A sample DOM writer. This sample program illustrates how to traverse a DOM
 * tree in order to print a document that is parsed.
 * 
 * @version
 */
public class DOMWriter {

  public static String writeToString(Node n) {
    StringWriter w = new StringWriter();
    PrintWriter pw = new PrintWriter(w);
    print(n, pw);
    pw.flush();
    return w.toString();
  }

  public static void print(Node node, PrintWriter out) {
    _print(node, out);
    out.flush();
  }

  private static void _print(Node node, PrintWriter out) {
    // is there anything to do?
    if (node == null) {
      return;
    }

    int type = node.getNodeType();
    switch (type) {
      // print document
      case Node.DOCUMENT_NODE: {
        NodeList children = node.getChildNodes();
        for (int iChild = 0; iChild < children.getLength(); iChild++) {
          _print(children.item(iChild), out);
        }
        break;
      }

        // print element with attributes
      case Node.ELEMENT_NODE: {
        out.print('<');
        out.print(node.getNodeName());
        Attr attrs[] = sortAttributes(node.getAttributes());
        for (int i = 0; i < attrs.length; i++) {
          Attr attr = attrs[i];
          out.print(' ');
          out.print(attr.getNodeName());
          out.print("=\"");
          out.print(normalize(attr.getValue()));
          out.print('"');
        }
        out.print('>');
        NodeList children = node.getChildNodes();
        if (children != null) {
          int len = children.getLength();
          for (int i = 0; i < len; i++) {
            _print(children.item(i), out);
          }
        }
        break;
      }

        // handle entity reference nodes
      case Node.ENTITY_REFERENCE_NODE: {
        out.print('&');
        out.print(node.getNodeName());
        out.print(';');
        break;
      }

        // print cdata sections
      case Node.CDATA_SECTION_NODE:
        int currentIndex = 0;
        String text = node.getNodeValue();
        while (currentIndex < text.length()) {
          out.print("<![CDATA[");
          int index = text.indexOf("]]>", currentIndex);
          if (index < 0) {
            out.print(text.substring(currentIndex, text.length() - currentIndex));
            out.print("]]>");
            break;
          }
          out.print(text.substring(currentIndex, index + 2 - currentIndex));
          out.print("]]><![CDATA[>]]>");
          currentIndex = index + 3;
        }
        break;

      // print text
      case Node.TEXT_NODE: {
        out.print(normalize(node.getNodeValue()));
        break;
      }

        // print processing instruction
      case Node.PROCESSING_INSTRUCTION_NODE: {
        out.print("<?");
        out.print(node.getNodeName());
        String data = node.getNodeValue();
        if (data != null && data.length() > 0) {
          out.print(' ');
          out.print(data);
        }
        out.print("?>");
        break;
      }
    }

    if (type == Node.ELEMENT_NODE) {
      out.print("</");
      out.print(node.getNodeName());
      out.print('>');
    }

  } // print(Node)

  /** Returns a sorted list of attributes. */
  static protected Attr[] sortAttributes(NamedNodeMap attrs) {
    int len = (attrs != null) ? attrs.getLength() : 0;
    Attr array[] = new Attr[len];
    for (int i = 0; i < len; i++) {
      array[i] = (Attr) attrs.item(i);
    }
    for (int i = 0; i < len - 1; i++) {
      String name = array[i].getNodeName();
      int index = i;
      for (int j = i + 1; j < len; j++) {
        String curName = array[j].getNodeName();
        if (curName.compareTo(name) < 0) {
          name = curName;
          index = j;
        }
      }
      if (index != i) {
        Attr temp = array[i];
        array[i] = array[index];
        array[index] = temp;
      }
    }

    return (array);

  } // sortAttributes(NamedNodeMap):Attr[]

  /** Normalizes the given string. */
  public static String normalize(String s) {
    StringBuffer str = new StringBuffer();

    int len = (s != null) ? s.length() : 0;
    for (int i = 0; i < len; i++) {
      char ch = s.charAt(i);
      switch (ch) {
        case '<': {
          str.append("&lt;");
          break;
        }
        case '>': {
          // ACCORDING to spec, only ]]> needs to be translated
          // but we make life easier here
          str.append("&gt;");
          break;
        }
        case '&': {
          str.append("&amp;");
          break;
        }
        case '"': {
          str.append("&quot;");
          break;
        }
        case '\r':
        case '\n': {
          // else, default append char
        }
        default: {
          str.append(ch);
        }
      }
    }

    return (str.toString());

  } // normalize(String):String
}
