package org.gugino.gamemanager.gfx.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import org.gugino.gamemanager.gfx.ui.enums.UIItemType;
import org.gugino.gamemanager.gfx.ui.enums.UIRenderLayer;
import org.gugino.gamemanager.gfx.ui.uiitems.UIButton;
import org.gugino.gamemanager.gfx.ui.uiitems.UIInputField;
import org.gugino.gamemanager.gfx.ui.uiitems.UIItem;
import org.gugino.gamemanager.gfx.ui.uiitems.UIPanel;
import org.gugino.gamemanager.gfx.ui.uiitems.UIProgressBar;
import org.gugino.gamemanager.gfx.ui.uiitems.UIString;
import org.gugino.gamemanager.util.StringHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class UIManager {
	private HashMap<String, UIItem> uiItems = new HashMap<>();

	private boolean showDebug = false;
	
	public UIManager() {}
	public UIManager(boolean _showDebug) {this.showDebug = _showDebug;}	
	
	//Update enabled UIItem(s)
	public void update(double _delta) {
		for(UIItem _s : uiItems.values()) {
			if(_s.isEnabled()) _s.update(_delta);
		}
	}
	
	//Render enabled UIItem(s)
	public void render(Graphics _g) {
		ArrayList<UIItem> _foregroundItems = new ArrayList<>();
		ArrayList<UIItem> _midgroundItems = new ArrayList<>();
		ArrayList<UIItem> _backgroundItems = new ArrayList<>();
		
		for(UIItem _s : uiItems.values()) {
			if(_s.isEnabled()) {
				if(_s.getRenderLayer() == UIRenderLayer.BACKGROUND) _backgroundItems.add(_s);
				if(_s.getRenderLayer() == UIRenderLayer.MID_GROUND) _midgroundItems.add(_s);
				if(_s.getRenderLayer() == UIRenderLayer.FOREGROUND) _foregroundItems.add(_s);
			}
		}

		for (UIItem _background : _backgroundItems) if(_background.isEnabled()) _background.render(_g);
		for (UIItem _midground : _midgroundItems) if(_midground.isEnabled()) _midground.render(_g);
		for (UIItem _foreground : _foregroundItems) if(_foreground.isEnabled()) _foreground.render(_g);
	}
	
	public void loadUILayoutFromXML(Document _xmlDocument) {
		ArrayList<UIItem> _createdUIItems = new ArrayList<UIItem>();
		
		NodeList _layoutNodes = _xmlDocument.getElementsByTagName("UILayout");
		
		if(_layoutNodes.getLength() <= 0) { if(showDebug) {System.err.println("Invaild UILayout, <UILayout> tag not found!");} return;
		}else if(_layoutNodes.getLength() > 1) { if(showDebug) {System.err.println("You can only have one <UILayout> tag!");} return;}

		Element _layout = (Element) _layoutNodes.item(0);
		
		NodeList _childNodes = _layout.getChildNodes();
		
		int _parentState = -1;
		
		if(_layout.hasAttribute("parentstate")) _parentState = Integer.parseInt(_layout.getAttribute("parentstate"));
		
		for (int _c = 0; _c < _childNodes.getLength(); _c++) {
			Node _currentNode = _childNodes.item(_c);

			//TODO: Add other UIItems
			
			String _uiID = "";
			double _uiX, _uiY;
			UIRenderLayer _uiLayer;
			
			if(_currentNode instanceof Element) {

				Element _currentElement = (Element) _currentNode;
				
				/*Grabs attributes for basic details all UIItems have and
				 * stores them inside a string array, then the values are parse
				 * into a more use able form then the UIItem is add to a 
				 * created UIItem ArrayList. Once all UIItems have been read from the
				 * file and add to the created list, it adds all UIItems into the UIManager's
				 * active items.
				 */
				String[] _panelDetails = getUIItemBasicDetails((Element) _currentNode);
				_uiID = _panelDetails[0];

				double[] _uiPos = StringHelper.parseCommaSeperatedStringToDouble(_panelDetails[1]);
				_uiX = _uiPos[0];
				_uiY = _uiPos[1];
				
				_uiLayer = parseStringToUILayer(_panelDetails[2]);
					
				
				switch(_currentNode.getNodeName()) {
					//Creates UIPanel
					case "UIPanel":			
						String _panelID = checkUIItemIDForEmpty(_uiID, UIItemType.UIPanel);
						
						//UI width/height
						int[] _splitSize = getUIItemSizeFromAttribute(_currentElement);
						
						UIPanel _createdPanel = new UIPanel(_panelID, getUIItemColorFromAttribute(_currentElement), _uiX, _uiY, _splitSize[0], _splitSize[1]);
						
						checkParentStateActivity(_createdPanel, _parentState);
	
						_createdPanel.setRenderLayer(_uiLayer);
						
						_createdUIItems.add(_createdPanel);
						break;
					
					//Creates UIString
					case "UIString":
						String _stringID = checkUIItemIDForEmpty(_uiID, UIItemType.UIString);
						
						String _stringValue = getUIItemValueFromAttribute(_currentElement);
						
						UIString _createdString = new UIString(_stringID, _stringValue,_uiX, _uiY, getUIItemColorFromAttribute(_currentElement));
						
						checkParentStateActivity(_createdString, _parentState);
		
						_createdString.setRenderLayer(_uiLayer);
						
						_createdUIItems.add(_createdString);
						break;
						
					//Creates UIImage
					case "UIImage":			
						String _imageID = checkUIItemIDForEmpty(_uiID, UIItemType.UIImage);
							
						//UI width/height
						int[] _imageSize = getUIItemSizeFromAttribute(_currentElement);
						
						BufferedImage _panelImage = null;
						
						if(_currentElement.hasAttribute("src")) _panelImage = StringHelper.parseStringToBufferedImage(_currentElement.getAttribute("src"));
						else if(showDebug) System.err.println("Failed to load image, not 'src' attribute");

						UIPanel _createdImage = new UIPanel(_imageID, _panelImage, _uiX, _uiY, _imageSize[0], _imageSize[1]);
							
						checkParentStateActivity(_createdImage, _parentState);
		
						_createdImage.setRenderLayer(_uiLayer);
							
						_createdUIItems.add(_createdImage);
						break;
						
					//Creates UIButton
					case "UIButton":
						String _buttonID = "";
						if(_uiID != null) _buttonID = checkUIItemIDForEmpty(_uiID, UIItemType.UIButton);
						else if(showDebug) { System.err.println("An 'id' attribute is required"); return;}
								
						//UI width/height
						int[] _buttonSize = getUIItemSizeFromAttribute(_currentElement);
						
						String _buttonText = getUIItemValueFromAttribute(_currentElement);
						
						Color _buttonTextColor = getUIItemTxtColorFromAttribute(_currentElement);
						
						UIButton _createdButton = null;
						
						if(_currentElement.hasAttribute("colors")) {
							Color[] _colors = StringHelper.parseCommaSeperatedStringToColors(getUIItemColorsFromAttribute(_currentElement));
							if(_colors.length >= 3) _createdButton = new UIButton(_buttonID, _uiX, _uiY, _buttonSize[0], _buttonSize[1], _colors[0], _colors[1], _colors[2], _buttonTextColor);
							else if(showDebug) System.err.println("You must provide 3 colors (normal,hover,clicked) - note: don't put spaces between colors");
						}else if(_currentElement.hasAttribute("srcs")) {
							BufferedImage[] _images = StringHelper.parseCommaSeperatedStringToBufferedImages(getUIItemImageSrcFromAttribute(_currentElement));
							_createdButton = new UIButton(_buttonID, _uiX, _uiY, _buttonSize[0], _buttonSize[1], _images[0], _images[1], _images[2], _buttonTextColor);
						}
							
						if(_createdButton != null) {
							checkParentStateActivity(_createdButton, _parentState);
							
							_createdButton.setButtonText(_buttonText);
							
							_createdButton.setRenderLayer(_uiLayer);
									
							_createdUIItems.add(_createdButton);	
						}
						break;
						
						//Creates UIInputField
						case "UIInputField":
							String _inputID = "";
							if(_uiID != null) _inputID = checkUIItemIDForEmpty(_uiID, UIItemType.UIInputField);
							else if(showDebug) { System.err.println("An 'id' attribute is required"); return;}
									
							//UI width/height
							int[] _inputSize = getUIItemSizeFromAttribute(_currentElement);
							
							UIInputField _createdInput = null;
							
							Color _fontColor = Color.black;
							
							if(_currentElement.hasAttribute("font-color")) _fontColor = StringHelper.parseStringToColor(_currentElement.getAttribute("font-color"));
							
							if(_currentElement.hasAttribute("color")) {
								_createdInput = new UIInputField(_inputID, _uiX, _uiY, _inputSize[0], _inputSize[1], getUIItemColorFromAttribute(_currentElement), _fontColor);
							}else if(_currentElement.hasAttribute("src")) {
								BufferedImage _image = null;
								_image = StringHelper.parseStringToBufferedImage(_currentElement.getAttribute("src"));
								_createdInput = new UIInputField(_inputID, _uiX, _uiY, _inputSize[0], _inputSize[1], _image, _fontColor);
							}
								
							if(_createdInput != null) {
								checkParentStateActivity(_createdInput, _parentState);
								
								_createdInput.setRenderLayer(_uiLayer);
										
								_createdUIItems.add(_createdInput);	
							}
							break;
							//Creates UIProgressBar
						case "UIProgressBar":
							String _barID = "";
							if(_uiID != null) _barID = checkUIItemIDForEmpty(_uiID, UIItemType.UIProgressBar);
							else if(showDebug) { System.err.println("An 'id' attribute is required"); return;}
									
							//UI width/height
							int[] _barSize = getUIItemSizeFromAttribute(_currentElement);
							
							UIProgressBar _createdBar = null;
				
							if(_currentElement.hasAttribute("bg-color")) {
								Color[] _colors = getUIProgressBarColorsFromAttributes(_currentElement);
								_createdBar = new UIProgressBar(_barID, _uiX, _uiY, _barSize[0], _barSize[1], _colors[0], _colors[1]);
							}else if(_currentElement.hasAttribute("src")) {
								BufferedImage[] _images = getUIProgressBarImagesFromAttributes(_currentElement);
								_createdBar = new UIProgressBar(_barID, _uiX, _uiY, _barSize[0], _barSize[1], _images[0], _images[1]);
							}
								
							if(_createdBar != null) {
								checkParentStateActivity(_createdBar, _parentState);
								
								int[] _values = getUIProgressBarValuesFromAttributes(_currentElement);
								_createdBar.setValues(_values[0], _values[1]);
								
								_createdBar.setRenderLayer(_uiLayer);
										
								_createdUIItems.add(_createdBar);	
							}
							break;
				}	
			}
		}
		
		addUIItems(_createdUIItems);
	}
	
	private void checkParentStateActivity(UIItem _item, int _parent) {
		if(_parent != -1) _item.setParentID(_parent);
		else _item.setEnabled(true);
	}
	
	private int[] getUIProgressBarValuesFromAttributes(Element _element) {
		int[] _values = new int[] {100, 100};
		if(_element.hasAttribute("max-value")) {
			String _maxString = _element.getAttribute("max-value");
			_values[0] = Integer.parseInt(_maxString);
		}else { _values[0] = 100; if(showDebug) System.err.println("No 'max-value' attribute found!");}
		
		if(_element.hasAttribute("current-value")) {
			String _currentString = _element.getAttribute("current-value");
			_values[1] = Integer.parseInt(_currentString);
		}else { _values[1] = _values[0]; if(showDebug) System.err.println("No 'current-value' attribute found!");}
		
		return _values;
	}
	
	private BufferedImage[] getUIProgressBarImagesFromAttributes(Element _element) {
		BufferedImage[] _images = new BufferedImage[] {null, null};
		
		if(_element.hasAttribute("bg-image")) {
			String _bgImageString = _element.getAttribute("bg-image");
			_images[0] = StringHelper.parseStringToBufferedImage(_bgImageString);
		}else {if(showDebug) System.err.println("No 'bg-image' attribute found!");}
		
		if(_element.hasAttribute("fill-image")) {
			String _fillImageString = _element.getAttribute("fill-image");
			_images[1] = StringHelper.parseStringToBufferedImage(_fillImageString);
		}else {if(showDebug) System.err.println("No 'fill-image' attribute found!");}
		
		return _images;
	}
	
	private Color[] getUIProgressBarColorsFromAttributes(Element _element) {
		Color[] _colors = new Color[] {Color.black, Color.black};
		
		if(_element.hasAttribute("bg-color")) {
			String _bgString = _element.getAttribute("bg-color");
			if(_bgString.charAt(0) == '#') _colors[0] = StringHelper.parseHEXStringToColor(_bgString);
			else _colors[0] = StringHelper.parseStringToColor(_bgString);
		}else {if(showDebug) System.err.println("No 'bg-color' attribute found!");}
		
		if(_element.hasAttribute("fill-color")) {
			String _fillString = _element.getAttribute("fill-color");
			if(_fillString.charAt(0) == '#') _colors[1] = StringHelper.parseHEXStringToColor(_fillString);
			else _colors[1] = StringHelper.parseStringToColor(_fillString);
		}else {if(showDebug) System.err.println("No 'fill-color' attribute found!");}
		
		return _colors;
	}
	
	//Returns a render layer enum from the inputed string
	private UIRenderLayer parseStringToUILayer(String _layer) {
		switch(_layer) {
			case "foreground":
				return UIRenderLayer.FOREGROUND;
			case "midground":
				return UIRenderLayer.MID_GROUND;
			case "background":
				return UIRenderLayer.BACKGROUND;
			default:
				return UIRenderLayer.BACKGROUND;
		}
	}
	
	//Returns string array with basic details that all items have (eg. ID, Position, RenderLayer)
	private String[] getUIItemBasicDetails(Element _element) {
		String[] _details = new String[3];
		if(_element.hasAttribute("id")) _details[0] = _element.getAttribute("id");
		if(_element.hasAttribute("position")) _details[1] = _element.getAttribute("position");
		if(_element.hasAttribute("layer")) _details[2] = _element.getAttribute("layer");
		return _details;
	}
	
	//Returns proper ID if none is provided, otherwise returns inputed ID
	private String checkUIItemIDForEmpty(String _id, UIItemType _itemType) {
		if(_id.isEmpty()) {
			if(_itemType == UIItemType.UIPanel) return "panel_";
			if(_itemType == UIItemType.UIButton) return "button_";
			if(_itemType == UIItemType.UIString) return "string_";
			if(_itemType == UIItemType.UIImage) return "image_";
			if(_itemType == UIItemType.UIInputField) return "input-field_";
		}
		
		return _id;	
	}
	
	private Color getUIItemTxtColorFromAttribute(Element _element) {
		if(_element.hasAttribute("txt-color")) {
			String _txtColor = _element.getAttribute("txt-color");
			
			Color _color = Color.black;
			
			if(_txtColor.charAt(0) == '#') {_color = StringHelper.parseHEXStringToColor(_txtColor);}
			else _color = StringHelper.parseStringToColor(_txtColor);
			
			return _color;
		}		
		
		if(showDebug) System.err.println("No 'txt-color' attribute found!");
		
		return Color.black;
	}
	
	private Color getUIItemColorFromAttribute(Element _element) {
		if(_element.hasAttribute("color")) {
			String _uiColor = _element.getAttribute("color");
			
			Color _color = Color.black;
			
			if(_uiColor.charAt(0) == '#') {
				try {
					_color = Color.decode(_uiColor);	
				} catch (NumberFormatException e) {
					_color = Color.black;
					if(showDebug) System.err.println("Invaild color format!");
				}
			}
			else _color = StringHelper.parseStringToColor(_uiColor);
			
			return _color;
		}
		
		if(showDebug) System.err.println("No 'color' attribute found!");
		
		return Color.black;
	}
	
	//Returns a String with the colors of the item
	private String getUIItemImageSrcFromAttribute(Element _element) {
		if(_element.hasAttribute("srcs")) {
			return _element.getAttribute("srcs");
		}else {
			return "";
		}
	}
	
	//Returns a String with the colors of the item
	private String getUIItemColorsFromAttribute(Element _element) {
		if(_element.hasAttribute("colors")) {
			return _element.getAttribute("colors");
		}else {
			return "black,black,black";
		}
	}
	
	//Returns a String with the text value of the item
	private String getUIItemValueFromAttribute(Element _element) {
		if(_element.hasAttribute("value")) {
			return _element.getAttribute("value");
		}else {
			return "Example String";
		}
	}
	
	//Returns an integer array with the size of the UIItem
	private int[] getUIItemSizeFromAttribute(Element _element) {
		if(_element.hasAttribute("size")) {
			return StringHelper.parseCommaSeperatedStringToInt(_element.getAttribute("size"));
		}else {
			return new int[] {0,0};
		}
	}
	
	//Add a list of UIItem(s) to the manager
	public void addUIItems(ArrayList<UIItem> _items) {
		for (UIItem _item : _items) {addUIItem(_item);}
	}
	
	//Add a single UIItem to the manager
	public void addUIItem(UIItem _item) {
		if(!uiItems.containsKey(_item.getUiID())) {uiItems.put(_item.getUiID(), _item); if(showDebug) System.out.println("UIItem with ID: " + _item.getUiID() + " has been added");}
		else if(showDebug) System.err.println("UIItem with ID: " + _item.getUiID() + " already exists");
	}
	
	//Removes UIItem from manager
	public void removeUIItem(String _uiID) {
		if(uiItems.containsKey(_uiID)) uiItems.remove(_uiID);
		else if(showDebug) System.err.println("UIItem with ID: " + _uiID + " doesn't exist");
	}
	
	
	//Returns the UIItem for the specified ID
	public UIItem getUIItemByID(String _uiID) {
		if(uiItems.containsKey(_uiID)) return uiItems.get(_uiID);
		if(showDebug) System.err.println("UIItem with ID: " + _uiID + " doesn't exist");
		return null;
	}
	
	//Returns the HashMap of all created UIItems
	public HashMap<String, UIItem> getUiItems() {
		return uiItems;
	}
}
