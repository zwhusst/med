/**
 * Copyright Voxa.com, Inc.
 */
package com.souyibao.shared.util;

import java.util.*;

import org.w3c.dom.*;

/**
 * This class is a utility class. Try to add some shortcut function for often
 * used DOM functions.
 * 
 * @author Yang Liu
 */
public class DOMUtil {

  protected static boolean isEmpty(String str) {
    return str == null || str.length() == 0;
  }

  protected static boolean supportDOM2(Element ele) {
    return isEmpty(ele.getLocalName());
  }

  protected static boolean matchName(Node ele, String name) {
    String local = ele.getLocalName();
    if (isEmpty(local)) {
      return ele.getNodeName().equals(name);
    } else {
      return local.equals(name);
    }
  }

  /**
   * Get the first child of parent node that with a specified type
   */
  public static Node getFirstChildOfType(Node parent, short type) {
    if (parent == null)
      return null;
    Node node = parent.getFirstChild();
    while (node != null) {
      if (node.getNodeType() == type)
        return node;
      node = node.getNextSibling();
    }
    return node;
  }

  /**
   * get first element child
   */
  public static Element getFirstElementChild(Node parent) {
    return (Element) getFirstChildOfType(parent, Node.ELEMENT_NODE);
  }

  /**
   * get the next element sibling node
   */
  public static Element getNextElementSibling(Node sibling) {
    if (sibling == null)
      return null;
    Node node = sibling.getNextSibling();
    while (node != null) {
      if (node.getNodeType() == Node.ELEMENT_NODE)
        return (Element) node;
      node = node.getNextSibling();
    }
    return null;
  }

  /**
   * <b>support DOM1,2</b><br>
   * get next element sibling of a particular tag name
   */
  public static Element getNextElementSibling(Node sibling, String tagname) {
    if (sibling == null)
      return null;
    Node node = sibling.getNextSibling();
    while (node != null) {
      if (node.getNodeType() == Node.ELEMENT_NODE && matchName(node, tagname))
        return (Element) node;
      node = node.getNextSibling();
    }
    return null;
  }

  /**
   * <b>support DOM1,2</b><br>
   * Get the first element child node with specified name
   */
  public static Element getFirstElementChild(Node parent, String tagname) {
    if (parent == null)
      return null;
    Node node = parent.getFirstChild();
    while (node != null) {
      if (node.getNodeType() == Node.ELEMENT_NODE && matchName(node, tagname))
        return (Element) node;
      node = node.getNextSibling();
    }
    return null;
  }

  /**
   * <b>support DOM1,2</b><br>
   * Get the first attribute value with specified name
   */
  public static String getFirstAttribute(Node parent, String attrName) {
    if (parent == null)
      return null;
    NamedNodeMap map = parent.getAttributes();
    if (map == null)
      return null;
    for (int i = 0, c = map.getLength(); i < c; i++) {
      Node itemi = map.item(i);
      if (matchName(itemi, attrName))
        return itemi.getNodeValue();
    }
    return null;
  }

  public static String getTextElementValue(Node node2) {
    StringBuffer buffer = new StringBuffer();
    Node node = node2.getFirstChild();
    while (node != null) {
      if (node.getNodeType() == Node.TEXT_NODE)
        buffer.append(node.getNodeValue());
      else if (node.getNodeType() == Node.CDATA_SECTION_NODE)
        buffer.append(node.getNodeValue());
      node = node.getNextSibling();
    }
    return buffer.toString();
  }

  /**
   * @param path path is simple path, formed by tagname "/" tagname ...
   */
  public static String getTextElementValue(Node node, String path) {
    StringTokenizer tokenizer = new StringTokenizer(path, "/");
    while (tokenizer.hasMoreTokens()) {
      String tag = tokenizer.nextToken();
      node = getFirstElementChild(node, tag);
      if (node == null)
        return null;
    }
    return getTextElementValue(node);
  }

  public static void setTextElementValue(Element ele, String value) {
    Node newChild = ele.getOwnerDocument().createTextNode(value);
    Node oldChild = ele.getFirstChild();
    while (oldChild != null) {
      if (oldChild.getNodeType() == Node.TEXT_NODE) {
        ele.replaceChild(newChild, oldChild);
        return;
      } else {
        oldChild = oldChild.getNextSibling();
      }
    }
    ele.appendChild(newChild);
  }

  /**
   * the parentNode is supposed to have same order as nodeOrder This function
   * will find the last child node with the elementName or the first one after
   * elementName in the order, or null if both not available.
   */
  // public static Element findElement(Element parentNode, String elementName,
  // String[] nodeOrder) {
  // int index = -1 ;
  // for( int i=0; i<nodeOrder.length; i++ ) {
  // if( nodeOrder[i].equals(elementName) ) {
  // index = i; break ;
  // }
  // }
  // if( index == -1 ) return null ;
  // int currentExpected = 0 ;
  // Node node = parentNode.getFirstChild() ;
  // while( node != null ) {
  // if( node.getNodeType() != Node.ELEMENT_NODE ) {
  // node = node.getNextSibling() ; continue ;
  // }
  // Element ele = (Element)node ;
  // String tn = ele.getTagName() ;
  // if( matchName(node, elementName) ) {
  // Node nextNode = ele.getNextSibling() ;
  // while( nextNode != null ) {
  // if( nextNode.getNodeType()==Node.ELEMENT_NODE ) {
  // Element next = (Element)nextNode ;
  // if( matchName(next, elementName) ) {
  // ele = next ; nextNode = ele.getNextSibling() ; continue ;
  // } else return ele ;
  // } else {
  // return ele ;
  // }
  // }
  // return ele ; // next == null, so return ele.
  // }
  // while(currentExpected!=index) {
  // if( tn.equals(nodeOrder[currentExpected]) )
  // break ;
  // currentExpected ++ ;
  // }
  // if( currentExpected >= index )
  // return ele ;
  // node = node.getNextSibling() ;
  // }
  // return null ;
  // }
  // public static void appendNodes(Element parentNode, Element[] elearray,
  // String[] nodeOrder) {
  // if( elearray.length == 0 ) return ;
  // String elementName = elearray[0].getTagName() ;
  // Element posi = findElement(parentNode, elementName, nodeOrder) ;
  // if( posi == null ) {
  // for( int i=0; i<elearray.length; i++ )
  // parentNode.appendChild(elearray[i]) ;
  // } else if( posi.getTagName().equals(elementName) ) {
  // Node refchild = posi.getNextSibling() ;
  // for( int i=0; i<elearray.length; i++ )
  // parentNode.insertBefore(elearray[i], refchild) ;
  // } else {
  // for( int i=0; i<elearray.length; i++ )
  // parentNode.insertBefore(elearray[i], posi) ;
  // }
  // }
  // public static void replaceNode(Element parentNode, Element newElement,
  // String[] nodeOrder) {
  // String elementName = newElement.getTagName() ;
  // Element posi = findElement(parentNode, elementName, nodeOrder) ;
  // if( posi == null )
  // parentNode.appendChild(newElement) ;
  // else if( posi.getTagName().equals(elementName) )
  // parentNode.replaceChild(newElement, posi) ;
  // else
  // parentNode.insertBefore(newElement, posi) ;
  // }
  public static void removeAllChildren(Element ele) {
    Node n;
    while ((n = ele.getFirstChild()) != null)
      ele.removeChild(n);
  }

  /**
   * <b>support DOM1,2</b><br>
   * will never return null
   * 
   * @param ele
   * @param tagname
   * @return
   */
  public static Vector getChildElements(Element ele, String tagname) {
    Node node = ele.getFirstChild();
    Vector v = new Vector();
    while (node != null) {
      if (node.getNodeType() == Node.ELEMENT_NODE && matchName(node, tagname))
        v.addElement((Element) node);
      node = node.getNextSibling();
    }
    return v;
  }

  public static List getChildElements(Element ele) {
    Node node = ele.getFirstChild();
    List v = new ArrayList();
    while (node != null) {
      if (node.getNodeType() == Node.ELEMENT_NODE)
        v.add((Element) node);
      node = node.getNextSibling();
    }
    return v;
  }

  public static String getElementInnerText(Element element) {
    StringBuffer buffer = new StringBuffer();
    getElementInnerText(element, buffer);
    return buffer.toString();
  }

  public static void getElementInnerText(Element element, StringBuffer buffer) {
    Node node = element.getFirstChild();
    while (node != null) {
      switch (node.getNodeType()) {
        case Node.ELEMENT_NODE:
          Element ele = (Element) node;
          getElementInnerText(ele, buffer);
          break;
        case Node.TEXT_NODE:
          buffer.append(node.getNodeValue());
          break;
        default:
          break; // ignore
      }
      node = node.getNextSibling();
    }
  }

  /**
   * <b>only support DOM1</b><br>
   * This function will create a text element, and append it to the parent and
   * then return it
   */
  public static Element appendTextElement(Element parent, String tagname,
      String textvalue) {
    Document doc = parent.getOwnerDocument();
    Element child = doc.createElement(tagname);
    if (textvalue != null) {
      Text textnode = doc.createTextNode(textvalue);
      child.appendChild(textnode);
    }
    parent.appendChild(child);
    return child;
  }

  /**
   * <b>only support DOM1</b><br>
   * 
   * @param parent
   * @param tagname
   * @return new Element
   */
  public static Element appendElement(Element parent, String tagname) {
    Document doc = parent.getOwnerDocument();
    Element child = doc.createElement(tagname);
    parent.appendChild(child);
    return child;
  }

  /**
   * Copies the source tree into the specified place in a destination tree. The
   * source node and its children are appended as children of the destination
   * node.
   * <p>
   * <em>Note:</em> This is an iterative implementation.
   */
  public static void copyInto(Element src, Element dest) {
    NamedNodeMap attributes = src.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      Attr attr = (Attr) attributes.item(i);
      String attrName = attr.getNodeName();
      String attrValue = attr.getNodeValue();

      // attribute specify flag
      if (attr.getSpecified()) {
        dest.setAttribute(attrName, attrValue);
      }

    }
    Node node = src.getFirstChild();
    while (node != null) {
      Node newnode = dest.getOwnerDocument().importNode(node, true);
      dest.appendChild(newnode);
      node = node.getNextSibling();
    }
  }

  /*
   * public static void copyInto(Node src, Node dest) throws DOMException {
   *  // get node factory Document factory = dest.getOwnerDocument(); boolean
   * domimpl = factory instanceof org.apache.xerces.dom.DocumentImpl;
   *  // placement variables Node start = src; Node parent = src; Node place =
   * src.getFirstChild();
   *  // traverse source tree while (place != null) {
   *  // copy this node Node node = null; int type = place.getNodeType(); switch
   * (type) { case Node.CDATA_SECTION_NODE: { node =
   * factory.createCDATASection(place.getNodeValue()); break; } case
   * Node.COMMENT_NODE: { node = factory.createComment(place.getNodeValue());
   * break; } case Node.ELEMENT_NODE: { Element element =
   * factory.createElement(place.getNodeName()); node = element; NamedNodeMap
   * attrs = place.getAttributes(); int attrCount = attrs.getLength(); for (int
   * i = 0; i < attrCount; i++) { Attr attr = (Attr)attrs.item(i); String
   * attrName = attr.getNodeName(); String attrValue = attr.getNodeValue();
   * element.setAttribute(attrName, attrValue);
   * 
   * //attribute specify flag if (domimpl && !attr.getSpecified()) {
   * ((org.apache.xerces.dom.AttrImpl)element.getAttributeNode(attrName)).setSpecified(false); } }
   * break; } case Node.ENTITY_REFERENCE_NODE: { node =
   * factory.createEntityReference(place.getNodeName()); break; } case
   * Node.PROCESSING_INSTRUCTION_NODE: { node =
   * factory.createProcessingInstruction(place.getNodeName(),
   * place.getNodeValue()); break; } case Node.TEXT_NODE: { node =
   * factory.createTextNode(place.getNodeValue()); break; } default: { throw new
   * IllegalArgumentException("can't copy node type, "+ type+" ("+
   * node.getNodeName()+')'); } } dest.appendChild(node);
   *  // iterate over children if (place.hasChildNodes()) { parent = place;
   * place = place.getFirstChild(); dest = node; }
   *  // advance else { place = place.getNextSibling(); while (place == null &&
   * parent != start) { place = parent.getNextSibling(); parent =
   * parent.getParentNode(); dest = dest.getParentNode(); } }
   *  }
   *  } // copyInto(Node,Node):void
   */

  // ------------------------------------------------------------------------------------
  // help function to read attributes
  /**
   * <b>only support DOM1</b><br>
   */
  public static boolean readBooleanAttribute(Element ele, String attrname,
      boolean defaultvalue) {
    String attr = ele.getAttribute(attrname);
    if (attr == null)
      return defaultvalue;
    if ("true".equalsIgnoreCase(attr)) {
      return true;
    } else if ("false".equalsIgnoreCase(attr)) {
      return false;
    } else
      return defaultvalue;
  }

  /**
   * <b>only support DOM1</b><br>
   */
  public static int readIntAttribute(Element ele, String attrname,
      int defaultvalue) {
    String attr = ele.getAttribute(attrname);
    if (attr == null || attr.trim().length() == 0)
      return defaultvalue;
    try {
      return Integer.parseInt(attr);
    } catch (Exception ex) {
      return defaultvalue;
    }
  }

  // ------------------------------------------------------------------------------------
  // XPath functions.
  /**
   * <b>only support DOM1</b><br>
   * if non is found will return an empty array. will not return null
   */
  public static Element[] getDescendentElementsByTag(Element ancester,
      String tag) {
    NodeList nl = ancester.getElementsByTagName(tag);
    if (nl == null) {
      return new Element[0];
    } else {
      Element[] ret = new Element[nl.getLength()];
      for (int i = 0; i < ret.length; i++) {
        ret[i] = (Element) nl.item(i);
      }
      return ret;
    }
  }

  public static String cleanPrefix(String nodeName) {
    if (nodeName == null)
      return null;
    int n = nodeName.indexOf(':');
    if (n == -1)
      return nodeName;
    else
      return nodeName.substring(n + 1);
  }

  public static Element cutNameSpace(Element ele) {
    Element e = ele.getOwnerDocument().createElement(
        cleanPrefix(ele.getNodeName()));

    NamedNodeMap attrs = ele.getAttributes();

    for (int i = 0; i < attrs.getLength(); i++)
      e.setAttribute(cleanPrefix(attrs.item(i).getNodeName()),
          attrs.item(i).getNodeValue());
    NodeList nodelist = ele.getChildNodes();
    for (int i = 0; i < nodelist.getLength(); i++) {
      if (nodelist.item(i).getNodeType() == Node.ELEMENT_NODE)
        copyElementWithOutNameSpace((Element) nodelist.item(i), e);
      else
        e.appendChild(nodelist.item(i).cloneNode(true));
    }
    return e;
  }

  private static void copyElementWithOutNameSpace(Element ele, Element parent) {
    NamedNodeMap attrs = ele.getAttributes();
    Element e = parent.getOwnerDocument().createElement(
        cleanPrefix(ele.getNodeName()));
    for (int i = 0; i < attrs.getLength(); i++)
      e.setAttribute(cleanPrefix(attrs.item(i).getNodeName()),
          attrs.item(i).getNodeValue());
    parent.appendChild(e);
    NodeList nodelist = ele.getChildNodes();
    for (int i = 0; i < nodelist.getLength(); i++) {
      if (nodelist.item(i).getNodeType() == Node.ELEMENT_NODE)
        copyElementWithOutNameSpace((Element) nodelist.item(i), e);
      else
        e.appendChild(nodelist.item(i).cloneNode(true));
    }
  }

  public static void cutDefaultNameSpace(Element ele) {
    ele.removeAttribute("xmlns");
    NodeList nodelist = ele.getChildNodes();
    for (int i = 0; i < nodelist.getLength(); i++) {
      Node node = nodelist.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        cutDefaultNameSpace(ele);
      }
    }
  }

  /**
   * this will duplicate the child to another element (even child is in another
   * document).
   * 
   * @return the new element appended.
   */
  public static Element importAppendElement(Element parent, Element child) {
    Element ele = (Element) parent.getOwnerDocument().importNode(child, true);
    parent.appendChild(ele);
    return ele;
  }

  /**
   * this will get the xpath string from to ancestor to the offspring. Added by
   * X.M Yang, maybe should removed later?
   * 
   * @return String: null if the offspring is actually not an offspring of the
   *         ancestor
   * @author X.M Yang
   */

  public static String getXPathString(Node ancestor, Node offspring) {
    StringBuffer pathbf = new StringBuffer();
    char tokenmark = '/';
    Node current = offspring;
    int index;
    Node p;
    while (current != ancestor && current instanceof Node) {
      p = current.getParentNode();
      switch (current.getNodeType()) {
        case Node.CDATA_SECTION_NODE:
        case Node.TEXT_NODE:
          index = 1;
          if (p != null) {
            NodeList l = p.getChildNodes();
            for (int i = 0; i < l.getLength(); i++) {
              Node n = l.item(i);
              if (n.getNodeType() == Node.CDATA_SECTION_NODE
                  || n.getNodeType() == Node.TEXT_NODE) {
                if (n == current)
                  break;
                index++;
              }
            }
          }
          if (index > 1) {
            pathbf.insert(0, "text()[" + index + "]");
          } else {
            pathbf.insert(0, "text()");
          }
          break;
        case Node.ATTRIBUTE_NODE:
          pathbf.insert(0, "@" + current.getNodeName());
          break;
        case Node.ELEMENT_NODE:
          index = 1;
          if (p != null) {
            NodeList l = p.getChildNodes();
            for (int i = 0; i < l.getLength(); i++) {
              Node n = l.item(i);
              if (n.getNodeType() == Node.ELEMENT_NODE) {
                if (n == current)
                  break;
                if (n.getNodeName() == current.getNodeName())
                  index++;
              }
            }
          }
          if (index > 0) {
            pathbf.insert(0, current.getNodeName() + "[" + index + "]");
          } else {
            pathbf.insert(0, current.getNodeName());
          }
          break;
      }
      pathbf.insert(0, tokenmark);
      current = current.getParentNode();
    }

    if (current != ancestor)// actually the offspring is not an offspring of the
                            // ancestor
      return null;
    return pathbf.toString();
  }

  public static Node[] getTextChildren(Element ele) {
    NodeList list = ele.getChildNodes();
    Vector buf = new Vector();
    if (list != null) {
      int size = list.getLength();
      for (int i = 0; i < size; i++) {
        Node ee = list.item(i);
        if (ee.getNodeType() == Node.TEXT_NODE) {
          buf.addElement(ee);
        }
      }
    }
    Node[] result = new Node[buf.size()];
    buf.copyInto(result);
    return result;
  }

  public static Node[] getCDataChildren(Element ele) {
    NodeList list = ele.getChildNodes();
    Vector buf = new Vector();
    if (list != null) {
      int size = list.getLength();
      for (int i = 0; i < size; i++) {
        Node ee = list.item(i);
        if (ee.getNodeType() == Node.CDATA_SECTION_NODE) {
          buf.addElement(ee);
        }
      }
    }
    Node[] result = new Node[buf.size()];
    buf.copyInto(result);
    return result;
  }

  /**
   * @param ele the Element you want to get the default name space
   * @return null if ele is null or can not find any namspace definition in the
   *         ele and its ancestors. DOM level 2
   */
  public static String getDefaultNameSpaceURI(Element ele) {
    if (ele == null)
      return null;
    String nsuri = ele.hasAttribute("xmlns") ? ele.getAttribute("xmlns") : null;
    Element currentele = ele;
    while (nsuri == null && (currentele.getParentNode() instanceof Element)) {
      currentele = (Element) currentele.getParentNode();
      nsuri = currentele.hasAttribute("xmlns")
          ? currentele.getAttribute("xmlns") : null;
    }
    return nsuri;
  }

  /**
   * @param ele
   * @param prefix
   * @return null if ele is null or ns is null or not found the prefix
   *         definition, this method will try to get the name space uri from its
   *         ancestors until getting one or nothing found DOM level 2
   */
  public static String getNameSpaceURI(Element ele, String prefix) {
    if (ele == null)
      return null;
    String nsattrname = (prefix == null || prefix.length() == 0) ? "xmlns"
        : "xmlns:" + prefix;
    String nsuri = ele.hasAttribute(nsattrname) ? ele.getAttribute(nsattrname)
        : null;
    Element currentele = ele;
    while (nsuri == null && (currentele.getParentNode() instanceof Element)) {
      currentele = (Element) currentele.getParentNode();
      nsuri = currentele.hasAttribute(nsattrname)
          ? currentele.getAttribute(nsattrname) : null;
    }
    return nsuri;
  }

  /**
   * This method is different from org.w3c.dom.Element in that it gets only
   * direct child Elements. Returns a List of all the direct child Elements with
   * a given local name and namespace URI in the order in which they are
   * encountered when traverse the children of this Element.
   * 
   * @param ele
   * @param namespaceURI
   * @param localName
   * @return DOM level 2
   */
  public static List getElementsByTagNameNS(Element ele, String namespaceURI,
      String localName) {
    if (ele == null || namespaceURI == null || localName == null)
      throw new NullPointerException("Illegal argument, no null is allowed");
    List retList = new ArrayList();
    NodeList nodelist = ele.getChildNodes();
    for (int i = 0, size = nodelist.getLength(); i < size; i++) {
      Node ithnode = nodelist.item(i);
      if (ithnode.getNodeType() == Node.ELEMENT_NODE) {
        Element childele = (Element) ithnode;
        if (localName.equals(childele.getLocalName())
            && namespaceURI.equals(childele.getNamespaceURI()))
          retList.add(childele);
      }
    }
    return retList;
  }
  
	public static String getNodeText(Element ele) {
		Node textNode = ele.getFirstChild();
		if (textNode == null) {
			return "";
		}

		return ((Text) textNode).getData();
	}
}
/*
 * $Log: DOMUtil.java,v $
 * Revision 1.1  2002/04/04 13:14:37  andy
 * no message
 *
 */