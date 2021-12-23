/*
 * Copyright (C) Gleidson Neves da Silveira
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.gleidson28.global.controls.control;

import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.SizeConverter;
import io.github.gleidson28.global.controls.converters.FieldTypeConverter;
import io.github.gleidson28.global.controls.skin.GNTextFieldSkinT;
import io.github.gleidson28.global.controls.textField.LeadIconTypeConverter;
import io.github.gleidson28.global.controls.textField.TextFieldActionTypeConverter;
import io.github.gleidson28.global.controls.types.FieldType;
import io.github.gleidson28.global.material.icon.Icons;
import javafx.beans.DefaultProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.*;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/12/2021fasd
 */
@DefaultProperty("control")
@SuppressWarnings("unused")
public class GNTextField extends TextField {

    private StringProperty message = new SimpleStringProperty(this, "message", "Error");

    public GNTextField() {
        getStyleClass().add("gn-text-field");
    }

    private List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    private final StyleableBooleanProperty floatPrompt
            = new StyleableBooleanProperty(false) {

        @Override
        public CssMetaData<GNTextField, Boolean> getCssMetaData() {
            return StyleableProperties.FLOAT_PROMPT;
        }

        @Override
        public Object getBean () {
            return this;
        }

        @Override
        public String getName () {
            return "FLOAT_PROMPT";
        }
    };

    private final StyleableObjectProperty<FieldType> fieldType
            = new StyleableObjectProperty<FieldType>(FieldType.OUTLINED)  {

        @Override
        public CssMetaData<? extends Styleable, FieldType> getCssMetaData() {
            return StyleableProperties.FIELD_TYPE;
        }

        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "FIELD_TYPE";
        }
    };

    private final StyleableObjectProperty<Icons> leadIconType
            = new StyleableObjectProperty<Icons>(Icons.NONE) {

        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "leadIconType";
        }

        @Override
        public CssMetaData<? extends Styleable, Icons> getCssMetaData() {
            return StyleableProperties.LEAD_ICON_TYPE;
        }
    };


    private final StyleableObjectProperty<Icons> actionType
            = new StyleableObjectProperty<Icons>(Icons.NONE) {

        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "actionType";
        }

        @Override
        public CssMetaData<? extends Styleable, Icons> getCssMetaData() {
            return StyleableProperties.ACTION_TYPE;
        }
    };

    private final StyleableObjectProperty<Number> maxLength
            = new StyleableObjectProperty<Number>(-1D) {

        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "maxLength";
        }

        @Override
        public CssMetaData<? extends Styleable, Number> getCssMetaData() {
            return StyleableProperties.MAX_LENGTH;
        }
    };

    private final StyleableBooleanProperty visibleCount
            = new StyleableBooleanProperty(false) {

        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "visibleCount";
        }

        @Override
        public CssMetaData<? extends Styleable, Boolean> getCssMetaData() {
            return StyleableProperties.VISIBLE_COUNT;
        }
    };

    private final StyleableBooleanProperty visibleMessage
            = new StyleableBooleanProperty(false) {

        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "visibleMessage";
        }

        @Override
        public CssMetaData<? extends Styleable, Boolean> getCssMetaData() {
            return StyleableProperties.VISIBLE_MESSAGE;
        }
    };


    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        if (this.STYLEABLES == null) {
            List<CssMetaData<? extends Styleable, ?>> styleables =
                    new ArrayList<>(getClassCssMetaData());
            styleables.addAll(getClassCssMetaData());
            styleables.addAll(Control.getClassCssMetaData());
            this.STYLEABLES = Collections.unmodifiableList(styleables);

        }
        return this.STYLEABLES;
    }

    private static class StyleableProperties {

        private final static List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        private static final CssMetaData<GNTextField, Boolean> FLOAT_PROMPT;
        private static final CssMetaData<GNTextField, FieldType> FIELD_TYPE;
        private static final CssMetaData<GNTextField, Icons> LEAD_ICON_TYPE;
        private static final CssMetaData<GNTextField, Icons> ACTION_TYPE;
        private static final CssMetaData<GNTextField, Number> MAX_LENGTH;
        private static final CssMetaData<GNTextField, Boolean> VISIBLE_COUNT;
        private static final CssMetaData<GNTextField, Boolean> VISIBLE_MESSAGE;

        @Contract(pure = true)
        private StyleableProperties() {}

        static {

            FLOAT_PROMPT = new CssMetaData<GNTextField, Boolean>(
                    "-gn-float-prompt",
                            BooleanConverter.getInstance(),false) {

                        @Override
                        public boolean isSettable(GNTextField styleable) {
                            return styleable.floatPrompt == null ||
                                    !styleable.floatPrompt.isBound();
                        }

                        @Override
                        @SuppressWarnings("unchecked")
                        public StyleableProperty<Boolean> getStyleableProperty(GNTextField styleable) {
                            return (StyleableProperty<Boolean>)
                                    styleable.floatPromptProperty();
                        }
                    };

            FIELD_TYPE = new CssMetaData<GNTextField, FieldType>(
                    "-gn-field-type",
                    FieldTypeConverter.getInstance()) {

                @Override
                public boolean isSettable(GNTextField styleable) {
                    return styleable.fieldType == null ||
                            !styleable.fieldType.isBound();
                }

                @Override
                @SuppressWarnings("unchecked")
                public StyleableProperty<FieldType> getStyleableProperty(GNTextField styleable) {
                    return (StyleableProperty<FieldType>) styleable.fieldTypeProperty();
                }
            };

            LEAD_ICON_TYPE = new CssMetaData<GNTextField, Icons>(
                    "-gn-lead-icon", LeadIconTypeConverter.getInstance()) {
                @Override
                public boolean isSettable(GNTextField styleable) {
                    return styleable.leadIconType == null ||
                            !styleable.leadIconType.isBound();
                }

                @Override
                @SuppressWarnings("unchecked")
                public StyleableProperty<Icons> getStyleableProperty(GNTextField styleable) {
                    return (StyleableProperty<Icons>) styleable.leadIconTypeProperty();
                }
            };

            ACTION_TYPE = new CssMetaData<GNTextField, Icons>(
                    "-gn-action-type", TextFieldActionTypeConverter.getInstance()) {
                @Override
                public boolean isSettable(GNTextField styleable) {
                    return styleable.actionType == null ||
                            !styleable.actionType.isBound();
                }

                @Override
                public StyleableProperty<Icons> getStyleableProperty(GNTextField styleable) {
                    return styleable.actionTypeProperty();
                }
            };


            MAX_LENGTH = new CssMetaData<GNTextField, Number>(
                "-gn-max-length", SizeConverter.getSizeConverter()
            ) {
                @Override
                public boolean isSettable(GNTextField styleable) {
                    return styleable.maxLength == null ||
                            !styleable.maxLength.isBound();
                }

                @Override
                public StyleableProperty<Number> getStyleableProperty(GNTextField styleable) {
                    return styleable.maxLengthProperty();
                }
            };

            VISIBLE_COUNT = new CssMetaData<GNTextField, Boolean>(
                    "-gn-visible-count",
                    BooleanConverter.getInstance()) {

                @Override
                public boolean isSettable(GNTextField styleable) {
                    return styleable.visibleCount == null ||
                            !styleable.visibleCount.isBound();
                }

                @Override
                public StyleableProperty<Boolean> getStyleableProperty(GNTextField styleable) {
                    return styleable.visibleCountProperty();
                }
            };

            VISIBLE_MESSAGE = new CssMetaData<GNTextField, Boolean>(
                    "-gn-visible-message",
                    BooleanConverter.getInstance()) {

                @Override
                public boolean isSettable(GNTextField styleable) {
                    return styleable.visibleMessage == null ||
                            !styleable.visibleMessage.isBound();
                }

                @Override
                public StyleableProperty<Boolean> getStyleableProperty(GNTextField styleable) {
                    return styleable.visibleMessageProperty();
                }
            };

            List<CssMetaData<? extends Styleable, ?>> styleables
                    = new ArrayList<>(Control.getClassCssMetaData());
            Collections.addAll(styleables,
                    FLOAT_PROMPT, FIELD_TYPE, LEAD_ICON_TYPE, ACTION_TYPE,
                    MAX_LENGTH, VISIBLE_COUNT, VISIBLE_MESSAGE);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @Contract(pure = true)
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.CHILD_STYLEABLES;
    }

    @Override
    protected Skin<?> createDefaultSkin () {
        return new GNTextFieldSkinT(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return getClass().getResource("/theme/css/controls.css").toExternalForm();
    }

    public BooleanProperty floatPromptProperty () {
        return floatPrompt;
    }

    public boolean isFloatPrompt () {
        return floatPrompt.getValue();
    }

    public void setFloatPrompt (boolean value) {
        floatPrompt.set(value);
    }

    public FieldType getFieldType() {
        return fieldType.get();
    }

    public ObjectProperty<FieldType> fieldTypeProperty() {
        return fieldType;
    }

    public void setButtonType(FieldType buttonType) {
        this.fieldType.set(buttonType);
    }

    public Icons getLeadIconType() {
        return leadIconType.get();
    }

    public ObjectProperty<Icons> leadIconTypeProperty() {
        return leadIconType;
    }

    public void setLeadIconType(Icons leadIconType) {
        this.leadIconType.set(leadIconType);
    }

    public Number getMaxLength() {
        return maxLength.get();
    }

    public StyleableObjectProperty<Number> maxLengthProperty() {
        return maxLength;
    }

    public void setMaxLength(Number maxLength) {
        this.maxLength.set(maxLength);
    }

    public boolean isVisibleCount() {
        return visibleCount.get();
    }

    public StyleableBooleanProperty visibleCountProperty() {
        return visibleCount;
    }

    public void setVisibleCount(boolean visibleCount) {
        this.visibleCount.set(visibleCount);
    }

    public boolean isVisibleMessage() {
        return visibleMessage.get();
    }

    public StyleableBooleanProperty visibleMessageProperty() {
        return visibleMessage;
    }

    public void setVisibleMessage(boolean visibleMessage) {
        this.visibleMessage.set(visibleMessage);
    }

    public Icons getActionType() {
        return actionType.get();
    }

    public StyleableObjectProperty<Icons> actionTypeProperty() {
        return actionType;
    }

    public void setActionType(Icons actionType) {
        this.actionType.set(actionType);
    }

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }
}
