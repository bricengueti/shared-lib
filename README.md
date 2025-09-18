# 📦 Shared Lib — Gesafrik Core Library

**Shared-lib** est une bibliothèque centrale utilisée par les différents services de la plateforme **Gesafrik**. Elle regroupe des composants réutilisables, des configurations communes, des modèles de données, et des utilitaires essentiels pour garantir la cohérence et la maintenabilité du code à travers les microservices.

---

## 🚀 Fonctionnalités principales

- 🔧 **Configurations centralisées** (Swagger, sécurité, CORS, etc.)
- 🧩 **Modèles de données partagés** (`UserContext`, `Society`, etc.)
- 🛡️ **Gestion des permissions et rôles**
- 📚 **Documentation OpenAPI intégrée**
- 🧠 **Utilitaires communs** (validation, mapping, etc.)

---

## 📁 Structure du projet

```bash
shared-lib/
├── config/               # Configurations globales (Swagger, sécurité, etc.)
├── dto/                  # Objets de transfert de données (UserContext, etc.)
├── enums/                # Énumérations partagées
├── exceptions/           # Gestion des erreurs personnalisées
├── utils/                # Fonctions utilitaires réutilisables
└── README.md             # Ce fichier


⚙️ Installation (Maven)
Pour intégrer shared-lib dans un projet Maven, assure-toi que le module est bien publié dans ton registre local ou distant.

1. Ajouter la dépendance dans pom.xml
<dependency>
  <groupId>com.gesafrik</groupId>
  <artifactId>shared-lib</artifactId>
  <version>1.0.0</version>
</dependency>
Remplace 1.0.0 par la version actuelle du module si elle diffère.

