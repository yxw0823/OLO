-- ----------------------------
-- Table structure for "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"
-- ----------------------------
-- DROP TABLE "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS";
CREATE TABLE "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS" (
	"ORDER_ID" VARCHAR2(255 BYTE) NULL ,
	"USER_ID" VARCHAR2(255 BYTE) NULL ,
	"SKU_ID" VARCHAR2(255 BYTE) NULL ,
	"CODE" VARCHAR2(255 BYTE) NULL ,
	"TITLE" VARCHAR2(255 BYTE) NULL ,
	"NUMBER" VARCHAR2(255 BYTE) NULL ,
	"PRICE" VARCHAR2(255 BYTE) NULL ,
	"CREATE_TIME" VARCHAR2(255 BYTE) NULL ,
	"CREATION_PEOPLE_ID" VARCHAR2(255 BYTE) NULL ,
	"UPDATE_TIME" VARCHAR2(255 BYTE) NULL ,
	"UPDATE_PEOPLE_ID" VARCHAR2(255 BYTE) NULL ,
	"SPREAD1" VARCHAR2(255 BYTE) NULL ,
	"SPREAD2" VARCHAR2(255 BYTE) NULL ,
	"SPREAD3" VARCHAR2(255 BYTE) NULL ,
	"SPREAD4" VARCHAR2(255 BYTE) NULL ,
	"SPREAD5" VARCHAR2(255 BYTE) NULL ,
	"OLOPDORDERDETAILS_ID" VARCHAR2(100 BYTE) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;

COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."ORDER_ID" IS '订单ID';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."USER_ID" IS '人员ID';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."SKU_ID" IS '物品ID';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."CODE" IS '商品编码';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."TITLE" IS '商品名称';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."NUMBER" IS '数量';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."PRICE" IS '价格';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."CREATION_PEOPLE_ID" IS '创建人ID';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."UPDATE_TIME" IS '更新时间';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."UPDATE_PEOPLE_ID" IS '更新人ID';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."SPREAD1" IS 'Spread1';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."SPREAD2" IS 'Spread2';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."SPREAD3" IS 'Spread3';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."SPREAD4" IS 'Spread4';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."SPREAD5" IS 'Spread5';
COMMENT ON COLUMN "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"."OLOPDORDERDETAILS_ID" IS 'ID';

-- ----------------------------
-- Indexes structure for table OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS
-- ----------------------------

-- ----------------------------
-- Checks structure for table "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"

-- ----------------------------

ALTER TABLE "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS" ADD CHECK ("OLOPDORDERDETAILS_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS"
-- ----------------------------
ALTER TABLE "C##NEWO"."OLO_PD_ORDER_DETAILSOLOPDORDERDETAILS" ADD PRIMARY KEY ("OLOPDORDERDETAILS_ID");
