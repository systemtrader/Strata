/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.basics.date;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

/**
 * Mock for serialization testing.
 */
@BeanDefinition
public class MockSerBean implements Bean {

  @PropertyDefinition
  private BusinessDayConvention bdConvention;

  @PropertyDefinition
  private HolidayCalendar holidayCalendar;

  @PropertyDefinition
  private DayCount dayCount;

  @PropertyDefinition
  private List<Object> objects = new ArrayList<>();

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code MockSerBean}.
   * @return the meta-bean, not null
   */
  public static MockSerBean.Meta meta() {
    return MockSerBean.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(MockSerBean.Meta.INSTANCE);
  }

  @Override
  public MockSerBean.Meta metaBean() {
    return MockSerBean.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the bdConvention.
   * @return the value of the property
   */
  public BusinessDayConvention getBdConvention() {
    return bdConvention;
  }

  /**
   * Sets the bdConvention.
   * @param bdConvention  the new value of the property
   */
  public void setBdConvention(BusinessDayConvention bdConvention) {
    this.bdConvention = bdConvention;
  }

  /**
   * Gets the the {@code bdConvention} property.
   * @return the property, not null
   */
  public final Property<BusinessDayConvention> bdConvention() {
    return metaBean().bdConvention().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the holidayCalendar.
   * @return the value of the property
   */
  public HolidayCalendar getHolidayCalendar() {
    return holidayCalendar;
  }

  /**
   * Sets the holidayCalendar.
   * @param holidayCalendar  the new value of the property
   */
  public void setHolidayCalendar(HolidayCalendar holidayCalendar) {
    this.holidayCalendar = holidayCalendar;
  }

  /**
   * Gets the the {@code holidayCalendar} property.
   * @return the property, not null
   */
  public final Property<HolidayCalendar> holidayCalendar() {
    return metaBean().holidayCalendar().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the dayCount.
   * @return the value of the property
   */
  public DayCount getDayCount() {
    return dayCount;
  }

  /**
   * Sets the dayCount.
   * @param dayCount  the new value of the property
   */
  public void setDayCount(DayCount dayCount) {
    this.dayCount = dayCount;
  }

  /**
   * Gets the the {@code dayCount} property.
   * @return the property, not null
   */
  public final Property<DayCount> dayCount() {
    return metaBean().dayCount().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the objects.
   * @return the value of the property
   */
  public List<Object> getObjects() {
    return objects;
  }

  /**
   * Sets the objects.
   * @param objects  the new value of the property
   */
  public void setObjects(List<Object> objects) {
    this.objects = objects;
  }

  /**
   * Gets the the {@code objects} property.
   * @return the property, not null
   */
  public final Property<List<Object>> objects() {
    return metaBean().objects().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public MockSerBean clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      MockSerBean other = (MockSerBean) obj;
      return JodaBeanUtils.equal(getBdConvention(), other.getBdConvention()) &&
          JodaBeanUtils.equal(getHolidayCalendar(), other.getHolidayCalendar()) &&
          JodaBeanUtils.equal(getDayCount(), other.getDayCount()) &&
          JodaBeanUtils.equal(getObjects(), other.getObjects());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getBdConvention());
    hash = hash * 31 + JodaBeanUtils.hashCode(getHolidayCalendar());
    hash = hash * 31 + JodaBeanUtils.hashCode(getDayCount());
    hash = hash * 31 + JodaBeanUtils.hashCode(getObjects());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(160);
    buf.append("MockSerBean{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("bdConvention").append('=').append(JodaBeanUtils.toString(getBdConvention())).append(',').append(' ');
    buf.append("holidayCalendar").append('=').append(JodaBeanUtils.toString(getHolidayCalendar())).append(',').append(' ');
    buf.append("dayCount").append('=').append(JodaBeanUtils.toString(getDayCount())).append(',').append(' ');
    buf.append("objects").append('=').append(JodaBeanUtils.toString(getObjects())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code MockSerBean}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code bdConvention} property.
     */
    private final MetaProperty<BusinessDayConvention> bdConvention = DirectMetaProperty.ofReadWrite(
        this, "bdConvention", MockSerBean.class, BusinessDayConvention.class);
    /**
     * The meta-property for the {@code holidayCalendar} property.
     */
    private final MetaProperty<HolidayCalendar> holidayCalendar = DirectMetaProperty.ofReadWrite(
        this, "holidayCalendar", MockSerBean.class, HolidayCalendar.class);
    /**
     * The meta-property for the {@code dayCount} property.
     */
    private final MetaProperty<DayCount> dayCount = DirectMetaProperty.ofReadWrite(
        this, "dayCount", MockSerBean.class, DayCount.class);
    /**
     * The meta-property for the {@code objects} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<Object>> objects = DirectMetaProperty.ofReadWrite(
        this, "objects", MockSerBean.class, (Class) List.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "bdConvention",
        "holidayCalendar",
        "dayCount",
        "objects");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 196761939:  // bdConvention
          return bdConvention;
        case -30625866:  // holidayCalendar
          return holidayCalendar;
        case 1905311443:  // dayCount
          return dayCount;
        case -1659648748:  // objects
          return objects;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends MockSerBean> builder() {
      return new DirectBeanBuilder<MockSerBean>(new MockSerBean());
    }

    @Override
    public Class<? extends MockSerBean> beanType() {
      return MockSerBean.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code bdConvention} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<BusinessDayConvention> bdConvention() {
      return bdConvention;
    }

    /**
     * The meta-property for the {@code holidayCalendar} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<HolidayCalendar> holidayCalendar() {
      return holidayCalendar;
    }

    /**
     * The meta-property for the {@code dayCount} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<DayCount> dayCount() {
      return dayCount;
    }

    /**
     * The meta-property for the {@code objects} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<List<Object>> objects() {
      return objects;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 196761939:  // bdConvention
          return ((MockSerBean) bean).getBdConvention();
        case -30625866:  // holidayCalendar
          return ((MockSerBean) bean).getHolidayCalendar();
        case 1905311443:  // dayCount
          return ((MockSerBean) bean).getDayCount();
        case -1659648748:  // objects
          return ((MockSerBean) bean).getObjects();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 196761939:  // bdConvention
          ((MockSerBean) bean).setBdConvention((BusinessDayConvention) newValue);
          return;
        case -30625866:  // holidayCalendar
          ((MockSerBean) bean).setHolidayCalendar((HolidayCalendar) newValue);
          return;
        case 1905311443:  // dayCount
          ((MockSerBean) bean).setDayCount((DayCount) newValue);
          return;
        case -1659648748:  // objects
          ((MockSerBean) bean).setObjects((List<Object>) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
