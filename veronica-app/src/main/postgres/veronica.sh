#!/bin/bash
set -e
psql -U postgres -d veronica -1 -f /tmp/veronica.sql
