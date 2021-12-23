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
package io.github.gleidson28.global.controls;

import com.sun.javafx.css.converters.PaintConverter;
import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/12/2018
 */
public class GNButtonSKin extends ButtonSkin {

    private Paint firstColor;

    private final StackPane rect = new StackPane();

    private final ObjectProperty<Duration> velocity =
            new SimpleObjectProperty<>(this, "velocity",
            Duration.millis(200)
    );

    private static final PseudoClass ANIMATED_PSEUDO_CLASS =
            PseudoClass.getPseudoClass("animated");

    private final BooleanProperty animated = new BooleanPropertyBase(false) {
        public void invalidated() {
            pseudoClassStateChanged(ANIMATED_PSEUDO_CLASS, get());
        }

        @Override public Object getBean() {
            return GNButtonSKin.this;
        }

        @Override public String getName() {
            return "animated";
        }
    };

    private StyleableObjectProperty<Paint> transitionColor ;

    private ObservableList<Node> customs = FXCollections.observableArrayList();

    public GNButtonSKin(Button control) {

        super(control);

        this.transitionColor = new SimpleStyleableObjectProperty<Paint>(TRANSITION_COLOR, this, "transitionColor");

        transitionColorProperty().addListener((observable, oldValue, newValue) -> {
            rect.setBackground(new Background(new BackgroundFill(newValue, CornerRadii.EMPTY, Insets.EMPTY)));
        });
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);
    }

    private static final CssMetaData<Button, Paint> TRANSITION_COLOR =
            new CssMetaData<Button, Paint>("-gn-transition-color", PaintConverter.getInstance(), Color.RED) {
                @Override
                public boolean isSettable(Button styleable) {
                    return ( (GNButtonSKin) styleable.getSkin()).transitionColor == null ||
                            !( (GNButtonSKin) styleable.getSkin()).transitionColor.isBound();
                }

                @Override
                public StyleableProperty<Paint> getStyleableProperty(Button styleable) {
                    if (styleable.getSkin() instanceof GNButtonSKin) {
                        return ( (GNButtonSKin) styleable.getSkin() ).transitionColorProperty();
                    } else return null;
                }
            };

    private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    static  {
        final List<CssMetaData<? extends Styleable, ?>> styleables =
                new ArrayList<>(SkinBase.getClassCssMetaData());
        styleables.add(TRANSITION_COLOR);
        STYLEABLES = Collections.unmodifiableList(styleables);
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return STYLEABLES;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    public Paint getTransitionColor() {
        return transitionColor.get();
    }

    public StyleableObjectProperty<Paint> transitionColorProperty() {
        return transitionColor;
    }

    public void setTransitionColor(Paint transitionColor) {
        this.transitionColor.set(transitionColor);
    }

    public boolean isAnimated() {
        return animated.get();
    }

    public BooleanProperty animatedProperty() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated.set(animated);
    }

    public Duration getVelocity() {
        return velocity.get();
    }

    public ObjectProperty<Duration> velocityProperty() {
        return velocity;
    }

    public void setVelocity(Duration velocity) {
        this.velocity.set(velocity);
    }
}