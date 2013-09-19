package de.timherbst.wau.util;

import javax.swing.plaf.FontUIResource;

import org.pushingpixels.substance.api.fonts.FontSet;

public class WrapperFontSet implements FontSet {
    /**
     * Extra size in pixels. Can be positive or negative.
     */
    private int extra;

    /**
     * The base Substance font set.
     */
    private FontSet delegate;

    /**
     * Creates a wrapper font set.
     * 
     * @param delegate
     *            The base Substance font set.
     * @param extra
     *            Extra size in pixels. Can be positive or negative.
     */
    public WrapperFontSet(FontSet delegate, int extra) {
      super();
      this.delegate = delegate;
      this.extra = extra;
    }

    /**
     * Returns the wrapped font.
     * 
     * @param systemFont
     *            Original font.
     * @return Wrapped font.
     */
    private FontUIResource getWrappedFont(FontUIResource systemFont) {
      return new FontUIResource(systemFont.getFontName(), systemFont
          .getStyle(), systemFont.getSize() + this.extra);
    }

    public FontUIResource getControlFont() {
      return this.getWrappedFont(this.delegate.getControlFont());
    }

    public FontUIResource getMenuFont() {
      return this.getWrappedFont(this.delegate.getMenuFont());
    }

    public FontUIResource getMessageFont() {
      return this.getWrappedFont(this.delegate.getMessageFont());
    }

    public FontUIResource getSmallFont() {
      return this.getWrappedFont(this.delegate.getSmallFont());
    }

    public FontUIResource getTitleFont() {
      return this.getWrappedFont(this.delegate.getTitleFont());
    }

    public FontUIResource getWindowTitleFont() {
      return this.getWrappedFont(this.delegate.getWindowTitleFont());
    }
  }