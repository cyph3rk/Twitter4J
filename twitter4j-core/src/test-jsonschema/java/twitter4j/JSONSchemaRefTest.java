package twitter4j;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSONSchemaRefTest {
    @Test
    void ref() {
        var extract = JSONSchema.extract("#/", """
                {
                "HostPort" : {
                        "type" : "object",
                        "description" : "A validly formatted URL.",
                        "example" : "https://example.com"
                        ,  "properties" : {
                          "host" : {
                            "type" : "string"
                          },
                          "port" : {
                            "type" : "integer"
                          }
                        }

                      },
                      "ProblemFields" : {
                        "type" : "object",
                        "required" : [ "type", "theHost"],
                        "properties" : {
                          "type" : {
                            "type" : "string",
                            "format" : "uri"
                          },
                          "theHost" : {
                            "$ref" : "#/HostPort"
                          }
                        }
                      }
                }""");

        assertEquals(6, extract.size());
        JSONSchema problemFields = extract.get("ProblemFields");
        assertEquals("""
                        @NotNull
                        private final String type;

                        @NotNull
                        private final HostPort theHost;
                        """,
                problemFields.asFieldDeclarations("twitter4j.v2",null).codeFragment());
        assertEquals("""
                        this.type = json.getString("type");
                        this.theHost = json.has("theHost") ? new HostPort(json.getJSONObject("theHost")) : null;""",
                problemFields.asConstructorAssignments("twitter4j.v2"));
        assertEquals("""
                        @NotNull
                        @Override
                        public String getType() {
                            return type;
                        }

                        @NotNull
                        @Override
                        public HostPort getTheHost() {
                            return theHost;
                        }
                        """,
                problemFields.asGetterImplementations("twitter4j.v2",null).codeFragment());
        assertEquals("""
                        /**
                         * @return type
                         */
                        @NotNull
                        String getType();
                        
                        /**
                         * @return theHost: A validly formatted URL.
                         */
                        @NotNull
                        HostPort getTheHost();
                        """,
                problemFields.asGetterDeclarations("twitter4j.v2",null).codeFragment());

        String javaImpl = problemFields.asJavaImpl("twitter4j", "twitter4j.v2");
        assertEquals("""
                        package twitter4j;
                                                
                        import org.jetbrains.annotations.NotNull;
                                                
                        import twitter4j.v2.HostPort;
                                                
                        /**
                         * ProblemFields
                         */
                        class ProblemFieldsImpl implements twitter4j.v2.ProblemFields {
                            @NotNull
                            private final String type;
                                                
                            @NotNull
                            private final HostPort theHost;
                                                
                            ProblemFieldsImpl(JSONObject json) {
                                this.type = json.getString("type");
                                this.theHost = json.has("theHost") ? new HostPort(json.getJSONObject("theHost")) : null;
                            }
                                                
                            @NotNull
                            @Override
                            public String getType() {
                                return type;
                            }
                                                
                            @NotNull
                            @Override
                            public HostPort getTheHost() {
                                return theHost;
                            }
                        }
                        """,
                javaImpl);

        String interfaceDeclaration = problemFields.asInterface("twitter4j.v2");
        assertEquals("""
                        package twitter4j.v2;
                                                
                        import org.jetbrains.annotations.NotNull;
                                                
                        /**
                         * ProblemFields
                         */
                        public interface ProblemFields {
                            /**
                             * @return type
                             */
                            @NotNull
                            String getType();
                            
                            /**
                             * @return theHost: A validly formatted URL.
                             */
                            @NotNull
                            HostPort getTheHost();
                        }
                        """,
                interfaceDeclaration);
    }
}
