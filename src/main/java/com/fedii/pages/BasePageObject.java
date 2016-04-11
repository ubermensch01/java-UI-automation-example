package com.fedii.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * Created by sfedii on 3/22/16.
 */
public class BasePageObject extends BasePage
{
  protected WebElement container;

  public BasePageObject(WebElement element)
  {
    super(((WrapsDriver) element).getWrappedDriver());
    container = element;
  }
}
