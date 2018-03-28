-- ----------------------------
-- Table structure for "C##NEWO"."OLO_PD_ORDEROLOPDORDER"
-- ----------------------------
-- DROP TABLE "C##NEWO"."OLO_PD_ORDEROLOPDORDER";
CREATE TABLE "C##NEWO"."OLO_PD_ORDEROLOPDORDER" (
	"STATE" VARCHAR2(255 BYTE) NULL ,
	"SEALED" VARCHAR2(255 BYTE) NULL ,
	"CREATE_TIME" VARCHAR2(255 BYTE) NULL ,
	"USER_ID" VARCHAR2(255 BYTE) NULL ,
	"CREATION_PEOPLE_ID" VARCHAR2(255 BYTE) NULL ,
	"UPDATE_TIME" VARCHAR2(255 BYTE) NULL ,
	"UPDATE_PEOPLE_ID" VARCHAR2(255 BYTE) NULL ,
	"SPREAD1" VARCHAR2(255 BYTE) NULL ,
	"SPREAD2" VARCHAR2(255 BYTE) NULL ,
	"SPREAD3" VARCHAR2(255 BYTE) NULL ,
	"SPREAD4" VARCHAR2(255 BYTE) NULL ,
	"SPREAD5" VARCHAR2(255 BYTE) NULL ,
	"OLOPDORDER_ID" VARCHAR2(100 BYTE) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;

COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDEROLOPDORDER"."STATE" IS '状态';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDEROLOPDORDER"."SEALED" IS '封存商品 默认 0  不封存 1 封存';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDEROLOPDORDER"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDEROLOPDORDER"."USER_ID" IS '下单人员ID';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDEROLOPDORDER"."CREATION_PEOPLE_ID" IS '创建人ID';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDEROLOPDORDER"."UPDATE_TIME" IS '更新时间';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDEROLOPDORDER"."UPDATE_PEOPLE_ID" IS '更新人ID';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDEROLOPDORDER"."SPREAD1" IS 'Spread1';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDEROLOPDORDER"."SPREAD2" IS 'Spread2';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDEROLOPDORDER"."SPREAD3" IS 'Spread3';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDEROLOPDORDER"."SPREAD4" IS 'Spread4';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDEROLOPDORDER"."SPREAD5" IS 'Spread5';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDEROLOPDORDER"."OLOPDORDER_ID" IS 'ID';

-- ----------------------------
-- Indexes structure for table OLO_PD_ORDEROLOPDORDER
-- ----------------------------

-- ----------------------------
-- Checks structure for table "C##NEWO"."OLO_PD_ORDEROLOPDORDER"

-- ----------------------------

ALTER TABLE "C##NEWO"."OLO_PD_ORDEROLOPDORDER" ADD CHECK ("OLOPDORDER_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table "C##NEWO"."OLO_PD_ORDEROLOPDORDER"
-- ----------------------------
ALTER TABLE "C##NEWO"."OLO_PD_ORDEROLOPDORDER" ADD PRIMARY KEY ("OLOPDORDER_ID");
