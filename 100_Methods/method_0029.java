  @Override
  @GwtIncompatible // Not supported.
  public Set<Entry<K, V>> entrySet() {
    // does not impact recency ordering
    Set<Entry<K, V>> es = entrySet;
    return (es != null) ? es : (entrySet = new EntrySet(this));
  }