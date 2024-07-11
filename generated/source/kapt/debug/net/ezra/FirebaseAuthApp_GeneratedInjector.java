package net.ezra;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = FirebaseAuthApp.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface FirebaseAuthApp_GeneratedInjector {
  void injectFirebaseAuthApp(FirebaseAuthApp firebaseAuthApp);
}
