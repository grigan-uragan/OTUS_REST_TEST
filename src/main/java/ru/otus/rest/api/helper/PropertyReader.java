package ru.otus.rest.api.helper;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
      "system:properties",
      "system:env",
      "file:src/test/resources/env/test.properties"
})
public interface PropertyReader extends Config {

  @Key("pet.store.base.uri")
  String petSoreBaseURI();

  @Key("pet.store.user.path")
  String petStoreUserPath();
}
