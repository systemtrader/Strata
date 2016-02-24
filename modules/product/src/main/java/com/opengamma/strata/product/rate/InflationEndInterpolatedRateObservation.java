/**
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.product.rate;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableValidator;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableSet;
import com.opengamma.strata.basics.index.Index;
import com.opengamma.strata.basics.index.PriceIndex;
import com.opengamma.strata.basics.index.PriceIndexObservation;
import com.opengamma.strata.collect.ArgChecker;

/**
 * Defines the observation of inflation figures from a price index with interpolation
 * where the start index value is known.
 * <p>
 * A typical application of this rate observation is payments of a capital indexed bond,
 * where the reference start month is the start month of the bond rather than start month
 * of the payment period. 
 * <p>
 * A price index is typically published monthly and has a delay before publication.
 * The rate observed by this instance will be based on the specified start index value
 * and two index observations relative to the end month.
 * Linear interpolation based on the number of days of the payment month is used
 * to find the appropriate value.
 */
@BeanDefinition
public final class InflationEndInterpolatedRateObservation
    implements RateObservation, ImmutableBean, Serializable {

  /**
   * The start index value.
   * <p>
   * The published index value of the start month.
   */
  @PropertyDefinition(validate = "ArgChecker.notNegativeOrZero")
  private final double startIndexValue;
  /**
   * The observation at the end.
   * <p>
   * The inflation rate is the ratio between the start index value and the interpolated end observations.
   * The end month is typically three months before the end of the period.
   */
  @PropertyDefinition(validate = "notNull")
  private final PriceIndexObservation endObservation;
  /**
   * The observation for interpolation at the end.
   * <p>
   * The inflation rate is the ratio between the start index value and the interpolated end observations.
   * The month is typically one month after the month of the end observation.
   */
  @PropertyDefinition(validate = "notNull")
  private final PriceIndexObservation endSecondObservation;
  /**
   * The positive weight used when interpolating.
   * <p>
   * Given two price index observations, typically in adjacent months, the weight is used
   * to determine the adjusted index value. The value is given by the formula
   * {@code (weight * price_index_1 + (1 - weight) * price_index_2)}.
   */
  @PropertyDefinition(validate = "ArgChecker.notNegative")
  private final double weight;

  //-------------------------------------------------------------------------
  /**
   * Creates an instance from an index, start index value and reference end month.
   * <p>
   * The second end observations will be one month later than the end month.
   * 
   * @param index  the index
   * @param startIndexValue  the start index value
   * @param referenceEndMonth  the reference end month
   * @param weight  the weight
   * @return the inflation rate observation
   */
  public static InflationEndInterpolatedRateObservation of(
      PriceIndex index,
      double startIndexValue,
      YearMonth referenceEndMonth,
      double weight) {

    return InflationEndInterpolatedRateObservation.builder()
        .startIndexValue(startIndexValue)
        .endObservation(PriceIndexObservation.of(index, referenceEndMonth))
        .endSecondObservation(PriceIndexObservation.of(index, referenceEndMonth.plusMonths(1)))
        .weight(weight)
        .build();
  }

  @ImmutableValidator
  private void validate() {
    ArgChecker.isTrue(
        endObservation.getIndex().equals(endSecondObservation.getIndex()), "Both observations must be for the same index");
    ArgChecker.inOrderNotEqual(
        endObservation.getFixingMonth(), endSecondObservation.getFixingMonth(), "endObservation", "endSecondObservation");
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the Price index.
   * 
   * @return the Price index
   */
  public PriceIndex getIndex() {
    return endObservation.getIndex();
  }

  //-------------------------------------------------------------------------
  @Override
  public void collectIndices(ImmutableSet.Builder<Index> builder) {
    builder.add(getIndex());
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code InflationEndInterpolatedRateObservation}.
   * @return the meta-bean, not null
   */
  public static InflationEndInterpolatedRateObservation.Meta meta() {
    return InflationEndInterpolatedRateObservation.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(InflationEndInterpolatedRateObservation.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static InflationEndInterpolatedRateObservation.Builder builder() {
    return new InflationEndInterpolatedRateObservation.Builder();
  }

  private InflationEndInterpolatedRateObservation(
      double startIndexValue,
      PriceIndexObservation endObservation,
      PriceIndexObservation endSecondObservation,
      double weight) {
    ArgChecker.notNegativeOrZero(startIndexValue, "startIndexValue");
    JodaBeanUtils.notNull(endObservation, "endObservation");
    JodaBeanUtils.notNull(endSecondObservation, "endSecondObservation");
    ArgChecker.notNegative(weight, "weight");
    this.startIndexValue = startIndexValue;
    this.endObservation = endObservation;
    this.endSecondObservation = endSecondObservation;
    this.weight = weight;
    validate();
  }

  @Override
  public InflationEndInterpolatedRateObservation.Meta metaBean() {
    return InflationEndInterpolatedRateObservation.Meta.INSTANCE;
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
   * Gets the start index value.
   * <p>
   * The published index value of the start month.
   * @return the value of the property
   */
  public double getStartIndexValue() {
    return startIndexValue;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the observation at the end.
   * <p>
   * The inflation rate is the ratio between the start index value and the interpolated end observations.
   * The end month is typically three months before the end of the period.
   * @return the value of the property, not null
   */
  public PriceIndexObservation getEndObservation() {
    return endObservation;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the observation for interpolation at the end.
   * <p>
   * The inflation rate is the ratio between the start index value and the interpolated end observations.
   * The month is typically one month after the month of the end observation.
   * @return the value of the property, not null
   */
  public PriceIndexObservation getEndSecondObservation() {
    return endSecondObservation;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the positive weight used when interpolating.
   * <p>
   * Given two price index observations, typically in adjacent months, the weight is used
   * to determine the adjusted index value. The value is given by the formula
   * {@code (weight * price_index_1 + (1 - weight) * price_index_2)}.
   * @return the value of the property
   */
  public double getWeight() {
    return weight;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      InflationEndInterpolatedRateObservation other = (InflationEndInterpolatedRateObservation) obj;
      return JodaBeanUtils.equal(startIndexValue, other.startIndexValue) &&
          JodaBeanUtils.equal(endObservation, other.endObservation) &&
          JodaBeanUtils.equal(endSecondObservation, other.endSecondObservation) &&
          JodaBeanUtils.equal(weight, other.weight);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(startIndexValue);
    hash = hash * 31 + JodaBeanUtils.hashCode(endObservation);
    hash = hash * 31 + JodaBeanUtils.hashCode(endSecondObservation);
    hash = hash * 31 + JodaBeanUtils.hashCode(weight);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(160);
    buf.append("InflationEndInterpolatedRateObservation{");
    buf.append("startIndexValue").append('=').append(startIndexValue).append(',').append(' ');
    buf.append("endObservation").append('=').append(endObservation).append(',').append(' ');
    buf.append("endSecondObservation").append('=').append(endSecondObservation).append(',').append(' ');
    buf.append("weight").append('=').append(JodaBeanUtils.toString(weight));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code InflationEndInterpolatedRateObservation}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code startIndexValue} property.
     */
    private final MetaProperty<Double> startIndexValue = DirectMetaProperty.ofImmutable(
        this, "startIndexValue", InflationEndInterpolatedRateObservation.class, Double.TYPE);
    /**
     * The meta-property for the {@code endObservation} property.
     */
    private final MetaProperty<PriceIndexObservation> endObservation = DirectMetaProperty.ofImmutable(
        this, "endObservation", InflationEndInterpolatedRateObservation.class, PriceIndexObservation.class);
    /**
     * The meta-property for the {@code endSecondObservation} property.
     */
    private final MetaProperty<PriceIndexObservation> endSecondObservation = DirectMetaProperty.ofImmutable(
        this, "endSecondObservation", InflationEndInterpolatedRateObservation.class, PriceIndexObservation.class);
    /**
     * The meta-property for the {@code weight} property.
     */
    private final MetaProperty<Double> weight = DirectMetaProperty.ofImmutable(
        this, "weight", InflationEndInterpolatedRateObservation.class, Double.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "startIndexValue",
        "endObservation",
        "endSecondObservation",
        "weight");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1656407615:  // startIndexValue
          return startIndexValue;
        case 82210897:  // endObservation
          return endObservation;
        case 1209389949:  // endSecondObservation
          return endSecondObservation;
        case -791592328:  // weight
          return weight;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public InflationEndInterpolatedRateObservation.Builder builder() {
      return new InflationEndInterpolatedRateObservation.Builder();
    }

    @Override
    public Class<? extends InflationEndInterpolatedRateObservation> beanType() {
      return InflationEndInterpolatedRateObservation.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code startIndexValue} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> startIndexValue() {
      return startIndexValue;
    }

    /**
     * The meta-property for the {@code endObservation} property.
     * @return the meta-property, not null
     */
    public MetaProperty<PriceIndexObservation> endObservation() {
      return endObservation;
    }

    /**
     * The meta-property for the {@code endSecondObservation} property.
     * @return the meta-property, not null
     */
    public MetaProperty<PriceIndexObservation> endSecondObservation() {
      return endSecondObservation;
    }

    /**
     * The meta-property for the {@code weight} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> weight() {
      return weight;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1656407615:  // startIndexValue
          return ((InflationEndInterpolatedRateObservation) bean).getStartIndexValue();
        case 82210897:  // endObservation
          return ((InflationEndInterpolatedRateObservation) bean).getEndObservation();
        case 1209389949:  // endSecondObservation
          return ((InflationEndInterpolatedRateObservation) bean).getEndSecondObservation();
        case -791592328:  // weight
          return ((InflationEndInterpolatedRateObservation) bean).getWeight();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code InflationEndInterpolatedRateObservation}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<InflationEndInterpolatedRateObservation> {

    private double startIndexValue;
    private PriceIndexObservation endObservation;
    private PriceIndexObservation endSecondObservation;
    private double weight;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(InflationEndInterpolatedRateObservation beanToCopy) {
      this.startIndexValue = beanToCopy.getStartIndexValue();
      this.endObservation = beanToCopy.getEndObservation();
      this.endSecondObservation = beanToCopy.getEndSecondObservation();
      this.weight = beanToCopy.getWeight();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1656407615:  // startIndexValue
          return startIndexValue;
        case 82210897:  // endObservation
          return endObservation;
        case 1209389949:  // endSecondObservation
          return endSecondObservation;
        case -791592328:  // weight
          return weight;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -1656407615:  // startIndexValue
          this.startIndexValue = (Double) newValue;
          break;
        case 82210897:  // endObservation
          this.endObservation = (PriceIndexObservation) newValue;
          break;
        case 1209389949:  // endSecondObservation
          this.endSecondObservation = (PriceIndexObservation) newValue;
          break;
        case -791592328:  // weight
          this.weight = (Double) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public InflationEndInterpolatedRateObservation build() {
      return new InflationEndInterpolatedRateObservation(
          startIndexValue,
          endObservation,
          endSecondObservation,
          weight);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the start index value.
     * <p>
     * The published index value of the start month.
     * @param startIndexValue  the new value
     * @return this, for chaining, not null
     */
    public Builder startIndexValue(double startIndexValue) {
      ArgChecker.notNegativeOrZero(startIndexValue, "startIndexValue");
      this.startIndexValue = startIndexValue;
      return this;
    }

    /**
     * Sets the observation at the end.
     * <p>
     * The inflation rate is the ratio between the start index value and the interpolated end observations.
     * The end month is typically three months before the end of the period.
     * @param endObservation  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder endObservation(PriceIndexObservation endObservation) {
      JodaBeanUtils.notNull(endObservation, "endObservation");
      this.endObservation = endObservation;
      return this;
    }

    /**
     * Sets the observation for interpolation at the end.
     * <p>
     * The inflation rate is the ratio between the start index value and the interpolated end observations.
     * The month is typically one month after the month of the end observation.
     * @param endSecondObservation  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder endSecondObservation(PriceIndexObservation endSecondObservation) {
      JodaBeanUtils.notNull(endSecondObservation, "endSecondObservation");
      this.endSecondObservation = endSecondObservation;
      return this;
    }

    /**
     * Sets the positive weight used when interpolating.
     * <p>
     * Given two price index observations, typically in adjacent months, the weight is used
     * to determine the adjusted index value. The value is given by the formula
     * {@code (weight * price_index_1 + (1 - weight) * price_index_2)}.
     * @param weight  the new value
     * @return this, for chaining, not null
     */
    public Builder weight(double weight) {
      ArgChecker.notNegative(weight, "weight");
      this.weight = weight;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(160);
      buf.append("InflationEndInterpolatedRateObservation.Builder{");
      buf.append("startIndexValue").append('=').append(JodaBeanUtils.toString(startIndexValue)).append(',').append(' ');
      buf.append("endObservation").append('=').append(JodaBeanUtils.toString(endObservation)).append(',').append(' ');
      buf.append("endSecondObservation").append('=').append(JodaBeanUtils.toString(endSecondObservation)).append(',').append(' ');
      buf.append("weight").append('=').append(JodaBeanUtils.toString(weight));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
